package com.epam.asw.sty.service.channel;

import com.epam.asw.sty.dao.ChannelDao;

import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;
import com.epam.asw.sty.service.item.ItemService;
import com.epam.asw.sty.service.rss.RssFeedReader;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.synd.SyndEntryImpl;
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

    @Resource(name="itemServiceImpl")
    ItemService itemService;

    private static final AtomicLong counter = new AtomicLong();
    private List<Channel> channels;

    public ChannelServiceImpl() {
        this.channels = new ArrayList<Channel>();
    }

    public List<Channel> findAllChannels() {
        return populateChannelsFromDB();
    }


    public Channel findById(String id) {
        for(Channel channel : populateChannelsFromDB()){
            if(channel.getId().equals(id)){
                return channel;
            }
        }
        return null;
    }

    public List<Channel> findByUser(String user) {
        List<Channel> channels = new ArrayList<Channel>();
        for(Channel channel : populateChannelsFromDB()){
            if(channel.getUser().equalsIgnoreCase(user)){
                channels.add(channel);
            }
        }
        return channels;
    }

    public Channel findByShortID(long shortid) {
        for(Channel channel : populateChannelsFromDB()){
            if(channel.getShortid() == shortid){
                return channel;
            }
        }
        return null;
    }


    public Channel findByLink(String link) {
        for(Channel channel : populateChannelsFromDB()){
            if(channel.getLink().equalsIgnoreCase(link)){
                return channel;
            }
        }
        return null;
    }

    public void saveChannel(Channel channel, String user) {

        channel.setId(new Channel().getId());
        channels = populateChannelsFromDB();
        if (channels.size() == 0) {
            counter.set(0);
        }
        else {
            counter.set(channels.get(channels.size()-1).getShortid());
        }
        channel.setShortid((int) counter.incrementAndGet());

        SyndFeed rssFeed = new RssFeedReader().obtainRSSFeed(channel.getLink());

        if(channel.getDescription().equals("")) {
            channel.setDescription(rssFeed.getDescription());
        }
        if(channel.getTitle().equals("")) {
            channel.setTitle(rssFeed.getTitle());
        }

        channel.setLanguage(rssFeed.getLanguage());
        channel.setPubDate(rssFeed.getPublishedDate());
        channel.setLastBuildDate(rssFeed.getPublishedDate());

        List<SyndEntryImpl> items = rssFeed.getEntries();
        channel.setItemsCount(items.size());
        channelDao.insertNewEntry(channel);
        itemService.convertSyndEntryToItem(items, channel.getShortid());
    }

    public void updateChannel(Channel channel) {
        channelDao.updateEntry(channel);
    }

    public void deleteChannelById(String id) {

        channels = populateChannelsFromDB();
        for (Iterator<Channel> iterator = channels.iterator(); iterator.hasNext(); ) {
            Channel channel = iterator.next();
            if (channel.getId().equals(id)) {
                itemService.deleteItemByChannelID(channel.getShortid());
                channelDao.removeEntryByID(id);
                break;
            }
        }
    }

    public boolean isChannelExist(Channel channel) {
        return findByLink(channel.getLink()) != null;
    }

    public void deleteAllChannels(){
        populateChannelsFromDB().clear();
    }


    public List<Channel> populateChannelsFromDB(){
        List<Channel> channels = channelDao.findAll();
        return channels;
    }

}