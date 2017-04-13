package com.epam.asw.sty.service.channel;



import com.epam.asw.sty.model.RssChannel;

import java.util.List;


public interface ChannelService {

    RssChannel findById(String id);

    List<RssChannel> findByUser(String user);

    void saveChannel(RssChannel rssChannel, String user);

    void updateChannel(RssChannel rssChannel);

    void deleteChannelById(String id);

    List<RssChannel> findAllChannels();

    RssChannel findByShortID(long shortid);

    void deleteAllChannels();

    public boolean isChannelExist(RssChannel rssChannel);

    public RssChannel findByLink(String link);

    List<RssChannel> populateChannelsFromDB();

}