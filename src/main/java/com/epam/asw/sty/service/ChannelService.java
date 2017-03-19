package com.epam.asw.sty.service;



import com.epam.asw.sty.model.Channel;

import java.util.List;


public interface ChannelService {

    Channel findById(long id);

    Channel findByUser(String user);

    void saveChannel(Channel channel);

    void updateChannel(Channel channel);

    void deleteChannelById(long id);

    List<Channel> findAllChannels();

    void deleteAllChannels();

    public boolean isChannelExist(Channel channel);

    List<Channel> populateChannelsFromDB();

    Object insertEntrytoDB(Channel channel);

}