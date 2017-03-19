package com.epam.asw.sty.service;

import com.epam.asw.sty.dao.ChannelDao;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;


public class SingleRssFeedSavertoDB {

    @Resource(name="channelDaoImpl")
    private ChannelDao channelDao;



    public Object saveRssFeedtoDB(SyndFeed rssFeed, ChannelDao channelDao) throws IOException, FeedException {
        //channelDao = new ChannelDaoImpl();
        //Object result = channelDao.insertNewEntry("SSSS");

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
