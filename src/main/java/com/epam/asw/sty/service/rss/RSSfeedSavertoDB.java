package com.epam.asw.sty.service.rss;

import com.epam.asw.sty.dao.ChannelDao;
import com.epam.asw.sty.model.Channel;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

import com.sun.syndication.feed.synd.Converter;

public class RSSfeedSavertoDB {

    @Resource(name="channelDaoImpl")
    ChannelDao channelDao;

    @Resource(name="converterForRSS20")
    Converter converter;

    public Object saveRssFeedtoDB(SyndFeed rssFeed) throws IOException, FeedException {
        //channelDao = new ChannelDaoImpl();
        //Object result = channelDao.insertNewEntry("SSSS");

        //Channel channel = (Channel) converter.createRealFeed(rssFeed);
        //Object result = channelDao.insertNewEntry(channel);
        Object result = channelDao.insertNewSiteEntry(rssFeed);


/*        System.out.println(rssFeed.getTitle());
        System.out.println(rssFeed.getDescription());
        System.out.println(rssFeed.getLink());
        System.out.println(rssFeed.getLanguage());
        //System.out.println(rssFeed.getEntries().toString());
        Object result = null;*/

        return result;
    }

}
