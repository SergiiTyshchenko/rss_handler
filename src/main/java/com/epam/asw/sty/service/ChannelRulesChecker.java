package com.epam.asw.sty.service;


import com.epam.asw.sty.model.Request;

public class ChannelRulesChecker {

    public Request superRequestCheck(Request request){
        if (request.getRequestor().equals("Sergii") || request.getRequestor().equals("Stas") ){
            request.setAssignee(request.getRequestor());
        }
        return request;
    }

}
