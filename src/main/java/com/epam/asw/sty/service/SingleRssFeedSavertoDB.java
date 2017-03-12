package com.epam.asw.sty.service;

import com.epam.asw.sty.dao.ChannelDao;
import com.epam.asw.sty.dao.ChannelDaoImpl;
import com.epam.asw.sty.model.Channel;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;


@Service("channelService")
@Transactional
public class SingleRssFeedSavertoDB {

    @Resource(name="channelDaoImpl")
    private ChannelDao channelDao;



    public Object saveRssFeedtoDB(SyndFeed rssFeed, ChannelDao channelDao) throws IOException, FeedException {
        //channelDao = new ChannelDaoImpl();
        //Object result = channelDao.insertNewEntry("SSSS");

                Object result = channelDao.insertNewSiteEntry(rssFeed.getTitle(),
                rssFeed.getDescription(),
                rssFeed.getLink(), rssFeed.getLanguage(), rssFeed.getLink(),
                rssFeed.getLanguage(), rssFeed.getTitle());


/*        System.out.println(rssFeed.getTitle());
        System.out.println(rssFeed.getDescription());
        System.out.println(rssFeed.getLink());
        System.out.println(rssFeed.getLanguage());
        //System.out.println(rssFeed.getEntries().toString());
        Object result = null;*/

        return result;
    }

}
