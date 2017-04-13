package com.epam.asw.sty.service.rss;


import com.epam.asw.sty.utils.RssCastList;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service("rssFeedReader")
@Transactional
public class RssFeedReader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SyndFeed rssFeed;
    private String feedURL;

    public RssFeedReader(String feedURL) {
        this.feedURL = feedURL;
    }

    public RssFeedReader() {

    }

    public List<SyndEntry> readRSSFeed () {
        rssFeed = obtainRSSFeed(getFeedURL());
        List<SyndEntry> entries = RssCastList.castList(SyndEntry.class,rssFeed.getEntries());
        return entries;
    }

    public SyndFeed obtainRSSFeed(String feedURL) {
        URL feedSource = null;
        try {
            feedSource = new URL(feedURL);
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException reaised", e);
        }
        SyndFeedInput input = new SyndFeedInput();
        try {
            rssFeed = input.build(new XmlReader(feedSource));
        } catch (FeedException e) {
            logger.error("FeedException reaised", e);
        } catch (IOException e) {
            logger.error("IOException reaised", e);
        }
        return rssFeed;
    }

    public String getFeedURL() {
        return feedURL;
    }

}
