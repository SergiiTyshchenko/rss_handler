package com.epam.asw.sty.service;


import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SingleRSSFeedReader {

    private SyndFeed rssFeed;
    private String feedURL;

    public SingleRSSFeedReader(String feedURL) {
        this.feedURL = feedURL;
    }


    public List<SyndEntry> readRSSFeed () throws IOException, FeedException {
        rssFeed = obtainRSSFeed(getFeedURL());
        List<SyndEntry> entries = rssFeed.getEntries();
        return entries;
    }

    public SyndFeed obtainRSSFeed(String feedURL) throws IOException, FeedException {
        URL feedSource = new URL(feedURL);
        SyndFeedInput input = new SyndFeedInput();
        rssFeed = input.build(new XmlReader(feedSource));
        return rssFeed;
    }

    public String getFeedURL() {
        return feedURL;
    }

}
