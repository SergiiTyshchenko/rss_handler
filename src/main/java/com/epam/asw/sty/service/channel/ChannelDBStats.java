package com.epam.asw.sty.service.channel;

import com.epam.asw.sty.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelDBStats {

    public Integer DBChannelCount(List<Channel> channels){
        Integer count = 0;
        for (Channel channel : channels){
            count++;
        }
        return count;
    }

    public List<String> ChannelsPerUser(List<Channel> channels, String user){
        List<String> stats = new ArrayList<String>();
        Integer countChannelsForUser=0;
        for (Channel channel : channels){
            if (channel.getUser().equalsIgnoreCase(user)){
                countChannelsForUser++;
            }
        }
        stats.add(0,user + " Channels: " + countChannelsForUser);
        return stats;
    }
}
