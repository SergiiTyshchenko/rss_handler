package com.epam.asw.sty.service;

import com.epam.asw.sty.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelDBtStats {

    public Integer DBChannelCount(List<Channel> channels){
        Integer count = 0;
        for (Channel channel : channels){
            count++;
        }
        return count;
    }

    public List<String> ChannelsPerUser(List<Channel> channels){
        List<String> stats = new ArrayList<String>();
        Integer count_sergii=0;
        for (Channel channel : channels){
            if (channel.getUser().equals("Sergii")){
                count_sergii++;
            }
        }
        stats.add(0,"Sergii Channels: " + count_sergii);
        return stats;
    }
}
