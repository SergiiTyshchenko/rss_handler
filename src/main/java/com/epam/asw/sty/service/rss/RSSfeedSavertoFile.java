package com.epam.asw.sty.service.rss;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class RSSFeedSavertoFile {

    public static void saveRssFeed(SyndFeed rssFeed) throws IOException, FeedException {
        String feedFileName = rssFeed.getLink();
        feedFileName = feedFileName.replaceAll("/", "~").replaceAll("https:","");
        Writer writer = new FileWriter("feedFiles/" + feedFileName + ".xml");
        SyndFeedOutput output = new SyndFeedOutput();
        output.output(rssFeed,writer);
        writer.close();
    }
}
