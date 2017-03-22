package com.epam.asw.sty.service;

import com.epam.asw.sty.dao.ChannelDao;

import com.epam.asw.sty.model.Channel;
import com.sun.syndication.feed.synd.Converter;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("channelServiceImpl")
@Transactional
public class ChannelServiceImpl implements ChannelService {

    @Resource(name="channelDaoImpl")
    ChannelDao channelDao;

    //@Resource(name="converterForRSS090")
    //Converter converter;

    private static final AtomicLong counter = new AtomicLong();
    private List<Channel> channels;

    public ChannelServiceImpl() {
        this.channels = new ArrayList<Channel>();
    }


    public List<Channel> findAllChannels() {
        return populateChannelsFromDB();
    }


    public Channel findById(long id) {
        for(Channel channel : populateChannelsFromDB()){
            if(channel.getId() == id){
                return channel;
            }
        }
        return null;
    }

    public Channel findByUser(String user) {
        for(Channel channel : populateChannelsFromDB()){
            if(channel.getUser().equalsIgnoreCase(user)){
                return channel;
            }
        }
        return null;
    }

    public void saveChannel(Channel channel) {
        //ChannelRulesChecker checker = new ChannelRulesChecker();
        channels = populateChannelsFromDB();
        if (channels.size() == 0) {
            counter.set(0);
        }
        else {
            counter.set(channels.get(channels.size()-1).getId());
        }
        channel.setId((int) counter.incrementAndGet());
        //checker.superChannelCheck(channel);


            String url = channel.getLink();
            SingleRSSFeedReader singleRSSFeedReader = new SingleRSSFeedReader(url);
            SingleRssFeedSavertoDB singleRssFeedSavertoDB = new SingleRssFeedSavertoDB();
            SyndFeed rssFeed = null;
        try {
            rssFeed = singleRSSFeedReader.obtainRSSFeed(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        }

        //channel = (Channel) converter.createRealFeed(rssFeed);
        channel.setDescription(rssFeed.getDescription());
        channel.setTitle(rssFeed.getTitle());
        channel.setLanguage(rssFeed.getLanguage());
        channel.setPubDate(rssFeed.getPublishedDate());
        channel.setUser("Sergii");
        channel.setLastBuildDate(rssFeed.getPublishedDate());
        channelDao.insertNewEntry(channel);
    }

    public void updateChannel(Channel channel) {
        channelDao.updateEntry(channel);
    }

    public void deleteChannelById(long id) {

        channels = populateChannelsFromDB();
        for (Iterator<Channel> iterator = channels.iterator(); iterator.hasNext(); ) {
            Channel channel = iterator.next();
            if (channel.getId() == id) {
                channelDao.removeEntryByID(id);
                break;
            }
        }
    }

    public boolean isChannelExist(Channel channel) {
        return findByUser(channel.getUser())!=null;
    }

    public void deleteAllChannels(){
        populateChannelsFromDB().clear();
    }


    public List<Channel> populateChannelsFromDB(){
        List<Channel> channels = channelDao.findAll();
        return channels;
    }

    @Override
    public Object insertEntrytoDB(Channel channel) {
        channels = populateChannelsFromDB();
        counter.set(channels.get(channels.size()-1).getId());
        channel.setId((int) counter.incrementAndGet());
        Object result = channelDao.insertNewEntry(channel);
        return result;
    }


}