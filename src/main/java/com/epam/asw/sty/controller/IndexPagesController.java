package com.epam.asw.sty.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@Controller
public class IndexPagesController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String HELLO_PAGE = "helloPage";

    //https://spring.io/blog/2015/01/12/spring-and-angular-js-a-secure-single-page-application
    @ResponseBody
    @RequestMapping(value="/admin", method = RequestMethod.GET)
    public Map<String,Object> adminIndex() {
        Map<String,Object> model = new HashMap<>();
        model.put("content", "Congratulation! You are admin!");
        return model;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutIndex(HttpServletRequest request, HttpServletResponse response)

    {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
            for(Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
        }

        return HELLO_PAGE;
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