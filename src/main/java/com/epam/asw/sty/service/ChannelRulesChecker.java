package com.epam.asw.sty.service;


import com.epam.asw.sty.model.Channel;

public class ChannelRulesChecker {

    public Channel superRequestCheck(Channel channel){
        if (channel.getUser().equals("Sergii")){
            channel.setUser(channel.getUser());
        }
        return channel;
    }

}
