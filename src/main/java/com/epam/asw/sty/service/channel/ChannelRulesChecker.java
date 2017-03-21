package com.epam.asw.sty.service.channel;


import com.epam.asw.sty.model.Channel;

public class ChannelRulesChecker {

    public Channel superChannelCheck(Channel channel){
        if (channel.getUser().equals("Sergii")){
            channel.setUser(channel.getUser());
        }
        return channel;
    }

}
