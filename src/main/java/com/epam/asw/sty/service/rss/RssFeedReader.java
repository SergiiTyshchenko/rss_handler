package com.epam.asw.sty.service.rss;


import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service("rssFeedReader")
@Transactional
public class RssFeedReader {

    private SyndFeed rssFeed;
    private String feedURL;

    public RssFeedReader(String feedURL) {
        this.feedURL = feedURL;
    }

    public RssFeedReader() {
    }

    public List<SyndEntry> readRSSFeed () {
        rssFeed = obtainRSSFeed(getFeedURL());
        List<SyndEntry> entries = rssFeed.getEntries();
        return entries;
    }

    public SyndFeed obtainRSSFeed(String feedURL) {
        URL feedSource = null;
        try {
            feedSource = new URL(feedURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SyndFeedInput input = new SyndFeedInput();
        try {
            rssFeed = input.build(new XmlReader(feedSource));
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rssFeed;
    }

    public String getFeedURL() {
        return feedURL;
    }

}
