package com.epam.asw.sty;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.io.WireFeedInput;
import com.sun.syndication.io.WireFeedOutput;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RssPublisher implements Serializable {
    private String rssFeedFilename = "rss/builds.xml";
    private int updateFrequency = 30;
    private String continuumXMLRPCUrl = "http://localhost:8080/continuum/xmlrpc";
    private String rssFormat = "rss_1.0";
    private String rssFeedTitle = "Company Build Results";

    //https://compscipleslab.wordpress.com/2012/11/22/creating-rss-feeds-for-your-website-using-rome-api/
    static Channel createChannel() {

        Channel channel = new Channel();

        channel.setFeedType("rss_2.0");

        channel.setTitle("Sumeet's RSS Channel");
        channel.setDescription("Test RSS Channel");
        channel.setLink("http://myIPAddress:portNo/WebAppName");

        return channel;
    }

    static Item createItem() {
        Item item = new Item();

        item.setTitle("Article first");

        return item;
    }

    static Channel addItemToChannel(Channel channel, Item item) {
        List items = (List) channel.getItems();
        if (items == null) {
            items = new ArrayList();
        }
        items.add(item);
        channel.setItems(items);
        return channel;
    }

    static boolean publishRSSChannel(Channel channel) {
        try {
            File RSSDoc = new File("rssfeed.rss");
            if (!RSSDoc.exists()) {
                RSSDoc.createNewFile();
            }
            WireFeedOutput wfo = new WireFeedOutput();
            wfo.output(channel, RSSDoc);
        } catch (Exception ee) {
            System.out.println(ee);
        }
        return true;
    }

    static Channel getExistingChannel(String url) {
        Channel channel = null;
        try {
            WireFeedInput wfi = new WireFeedInput();
            channel = (Channel) wfi.build(new File(url));
        } catch (Exception ee) {
            System.out.println(ee);
        }
        return channel;
    }

    static void displayItems(Channel channel) {
        List existingItems = channel.getItems();
        Iterator itr = existingItems.iterator();
        while (itr.hasNext()) {
            Item item = (Item) itr.next();
            System.out.println(item.getTitle());
        }
    }
}