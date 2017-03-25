package com.epam.asw.sty.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class IndexPagesController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CHANNEL_MANAGEMENT_ADMIN = "channelManagementForAdmin";
    private static final String CHANNEL_MANAGEMENT_USER = "channelManagementForUser";
    private static final String ITEM_FOR_CHANNEL_VIEW = "itemsForChannelView";
    private static final String HELLO_PAGE = "helloPage";
    private static final String ACCESS_DENIED = "accessDenied";


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

    @RequestMapping(value="/itemsForChannel", method = RequestMethod.GET)
    public String getItemsForChannelIndexPage() {
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


    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDeniedIndex() {
        logger.info("You are at: {} page.", ACCESS_DENIED);
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