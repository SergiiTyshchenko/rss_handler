package com.epam.asw.sty.service.channel;

import com.epam.asw.sty.dao.ChannelDao;

import com.epam.asw.sty.model.RssChannel;
import com.epam.asw.sty.service.item.ItemService;
import com.epam.asw.sty.service.rss.RssFeedReader;
import com.epam.asw.sty.utils.RssCastList;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
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
    private List<RssChannel> rssChannels;

    public ChannelServiceImpl() {
        this.rssChannels = new ArrayList<>();
    }

    public List<RssChannel> findAllChannels() {
        return populateChannelsFromDB();
    }


    public RssChannel findById(String id) {
        RssChannel rssChannel = channelDao.findByID(id);
        return rssChannel;
    }

    public List<RssChannel> findByUser(String user) {
        rssChannels = channelDao.findByUser(user);
        return rssChannels;
    }

    public RssChannel findByShortID(long shortid) {
        RssChannel rssChannel = channelDao.findByShortID(shortid);
        return rssChannel;
    }


    public RssChannel findByLink(String link) {
        RssChannel rssChannel = channelDao.findByLink(link);
        return rssChannel;
    }

    public void saveChannel(RssChannel rssChannel, String user) {

        rssChannel.setId(new RssChannel().getId());
        RssChannel rssChannelForCompare = channelDao.findLastAddedChannel();
        if (rssChannelForCompare == null) {
            counter.set(0);
        }
        else {
            counter.set(rssChannelForCompare.getShortid());
        }

        rssChannel.setShortid((int) counter.incrementAndGet());

        SyndFeed rssFeed = new RssFeedReader().obtainRSSFeed(rssChannel.getLink());

        if(("").equals(rssChannel.getDescription())) {
            rssChannel.setDescription(rssFeed.getDescription());
        }
        if(("").equals(rssChannel.getTitle())) {
            rssChannel.setTitle(rssFeed.getTitle());
        }

        rssChannel.setLanguage(rssFeed.getLanguage());
        rssChannel.setPubDate(rssFeed.getPublishedDate());
        rssChannel.setLastBuildDate(rssFeed.getPublishedDate());

        List<SyndEntryImpl> items = RssCastList.castList(SyndEntryImpl.class, rssFeed.getEntries());
        rssChannel.setItemsCount(items.size());
        channelDao.insertNewEntry(rssChannel);
        itemService.convertSyndEntryToItem(items, rssChannel.getShortid());
    }

    public void updateChannel(RssChannel rssChannel) {
        channelDao.updateEntry(rssChannel);
    }

    public void deleteChannelById(String id) {

        RssChannel rssChannel = channelDao.findByID(id);
        if (rssChannel.getId().equals(id)) {
            itemService.deleteItemByChannelID(rssChannel.getShortid());
            channelDao.removeEntryByID(id);
        }
    }

    public boolean isChannelExist(RssChannel rssChannel) {
        return findByLink(rssChannel.getLink()) != null;
    }

    public void deleteAllChannels(){
        populateChannelsFromDB().clear();
    }


    public List<RssChannel> populateChannelsFromDB(){
        List<RssChannel> rssChannels = channelDao.findAll();
        return rssChannels;
    }

}