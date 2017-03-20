package com.epam.asw.sty.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class MainPagesController {

    private static final String REQUEST_MANAGEMENT = "RequestManagement";
    private static final String CHANNEL_MANAGEMENT_ADMIN = "ChannelManagementForAdmin";
    private static final String CHANNEL_MANAGEMENT_USER = "ChannelManagementForUser";
    private static final String HELLO_PAGE = "helloPage";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index() {
        return HELLO_PAGE;
    }

    @RequestMapping(value="/my", method = RequestMethod.GET)
    public String getIndexPage() {
            return REQUEST_MANAGEMENT;
        }

    @RequestMapping(value="/ChannelsForUser", method = RequestMethod.GET)
    public String getUserChannelIndexPage() {
        return CHANNEL_MANAGEMENT_USER;
    }

    @RequestMapping(value="/ChannelsForAdmin", method = RequestMethod.GET)
    public String getAdminChannelIndexPage() {
        return CHANNEL_MANAGEMENT_ADMIN;
    }

    @ResponseBody
    @RequestMapping("/admin")
    public String adminIndex() {
        return "Hi at the admin page!";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String testIndex() {
        return "Site is working!";
    }

    @ResponseBody
    @RequestMapping("/testAjax")
    public Map<String,Object> testAjax() {
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("content", "ajaxTested!");
        return model;
    }

    //https://spring.io/blog/2015/01/12/spring-and-angular-js-a-secure-single-page-application
    @ResponseBody
    @RequestMapping("/resource")
    public Map<String,Object> home() {
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Congratulation!");
        return model;
    }

    //https://spring.io/guides/gs/spring-boot-cli-and-js/
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";

    }

    @RequestMapping(value = "/accessDenied")
    public String accessDenied() {
        logger.info("INFO message: you are at: accessDenied page.");
        return "accessDenied"; // logical view name
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal user) {


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