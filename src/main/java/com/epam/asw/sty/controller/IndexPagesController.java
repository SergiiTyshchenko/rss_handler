package com.epam.asw.sty.controller;


import com.epam.asw.sty.model.Item;
import com.epam.asw.sty.service.item.ItemService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringEscapeUtils;

@Controller
public class IndexPagesController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CHANNEL_MANAGEMENT_ADMIN = "channelManagementForAdmin";
    private static final String CHANNEL_MANAGEMENT_USER = "channelManagementForUser";
    private static final String ITEM_FOR_CHANNEL_VIEW = "itemsForChannelView";
    private static final String HELLO_PAGE = "helloPage";
    private static final String ACCESS_DENIED = "accessDenied";

    @Resource(name="itemServiceImpl")
    private ItemService itemService;


    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index() {
        return HELLO_PAGE;
    }


    @RequestMapping(value="/channelsForUser", method = RequestMethod.GET)
    public String getUserChannelIndexPage() {
        return CHANNEL_MANAGEMENT_USER;
    }


    @RequestMapping(value="/channelsForAdmin", method = RequestMethod.GET)
    public String getAdminChannelIndexPage() {
        return CHANNEL_MANAGEMENT_ADMIN;
    }

    @RequestMapping(value="/itemsForChannel", method = RequestMethod.GET, params={"channelID"})
    public String getItemsForChannelIndexPage(@RequestParam("channelID") int channelID, Model model) {
        String logDebugMessage = "Getting items for channel with short ID " + channelID;
        logger.debug("{}.", logDebugMessage);
/*        List<Item> items = itemService.findByChannelLink(channelID);
        String msg = "There are " + items.size() + " items for channel with short ID=" + channelID;
        logger.info("Items count {}.",  items.size());
        model.addAttribute("channelID", channelID);
        model.addAttribute("message", msg);
        model.addAttribute("item", items);*/

        JSONObject json = new JSONObject();
        json.put("channelID", channelID);
        String formattedJson = StringEscapeUtils.escapeHtml4(json.toString());
        model.addAttribute("newItemJson", formattedJson);

        return ITEM_FOR_CHANNEL_VIEW;
    }


    //https://spring.io/blog/2015/01/12/spring-and-angular-js-a-secure-single-page-application
    @ResponseBody
    @RequestMapping(value="/admin", method = RequestMethod.GET)
    public  Map<String,Object> adminIndex() {
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Congratulation! You are admin!");
        return model;
    }

    @ResponseBody
    @RequestMapping("/test")
    public String testIndex() {
        return "Site is working!";
    }



    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String LogoutIndex(HttpServletRequest request, HttpServletResponse response)

    {
        HttpSession session= request.getSession(false);
        SecurityContextHolder.clearContext();
        session= request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        return index();
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDeniedIndex() {
        logger.info("You are at: {} page.", ACCESS_DENIED);
        return ACCESS_DENIED;
    }

    @RequestMapping(value = "/alreadyExist", method = RequestMethod.GET)
    public String alreadyExistIndex() {
        logger.info("You are at: {} page routed from alreadyExistpage", ACCESS_DENIED);
        return ACCESS_DENIED;
    }


    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDeniedIndex(Principal user) {

        ModelAndView model = new ModelAndView();

        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }

        model.setViewName("403");
        logger.info("INFO message: you are at: {} page.", model.getViewName());
        return model;
    }


}