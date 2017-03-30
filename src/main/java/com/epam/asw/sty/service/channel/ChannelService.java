package com.epam.asw.sty.service.channel;



import com.epam.asw.sty.model.Channel;

import java.util.List;


public interface ChannelService {

    Channel findById(String id);

    List<Channel> findByUser(String user);

    void saveChannel(Channel channel);

    void updateChannel(Channel channel);

    void deleteChannelById(String id);

    List<Channel> findAllChannels();

    Channel findByShortID(long shortid);

    void deleteAllChannels();

    public boolean isChannelExist(Channel channel);

    public Channel findByLink(String link);

    List<Channel> populateChannelsFromDB();

    Object insertEntrytoDB(Channel channel);

}