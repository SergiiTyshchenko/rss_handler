package com.epam.asw.sty.service.channel;

import com.epam.asw.sty.model.RssChannel;

import java.util.ArrayList;
import java.util.List;

public class ChannelStatsService {

    public List<String> channelsPerUser(List<RssChannel> rssChannels, String user){
        List<String> stats = new ArrayList<>();
        int countChannelsForUser = 0;
        for (RssChannel rssChannel : rssChannels){
            if (rssChannel.getUser().equalsIgnoreCase(user)){
                countChannelsForUser++;
            }
        }
        stats.add(0,user + " Channels: " + countChannelsForUser);
        return stats;
    }
}
