package com.epam.asw.sty.run;


import com.epam.asw.sty.service.rss.RssFeedReader;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RssFeedCreationSample {
    public static void main(String[] args)  {

        //KB
        //http://www.javaworld.com/article/2077795/java-se/manage-rss-feeds-with-the-rome-api.html?page=2

        String url = "https://dou.ua/feed/";
        RssFeedReader RssFeedReader = new RssFeedReader(url);
        List <SyndEntry> entries;
        entries = RssFeedReader.readRSSFeed();
        saveRssFeed(RssFeedReader.obtainRSSFeed(url));

/*       SyndFeed feed = createFeed();
        List <SyndEntry> entries = feed.getEntries();

        if (entries == null) {
            entries = new ArrayList<SyndEntry>();
            entries.add(createEntry());
        }

        feed.setEntries(entries);*/


    }


    public static SyndFeed createFeed(){
        SyndFeed feed = new SyndFeedImpl();
        List feedCategories = new ArrayList<>();
        feed.setFeedType("rss_1.0");
        feed.setTitle("MyProject Build Results");
        feed.setLink("http://myproject.mycompany.com/continuum");
        feed.setDescription("Continuous build results for the MyProject project");
        //feed.setCategory("MyProject");
        feedCategories.add(0,"MyProject");
        return feed;
    }


    public static SyndEntry createEntry(){
        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle("BUILD SUCCESSFUL");
        entry.setLink("http://myproject.mycompany.com/continuum/build-results-1");
        entry.setPublishedDate(new Date());

        entry.setDescription(createDescription());
        entry.setCategories(createCategoies());
        return entry;
    }

    public static SyndContent createDescription(){
        SyndContent description = new SyndContentImpl();
        description.setType("text/html");
        description.setValue("The build was successful!");

        return description;
    }

    public static  List<SyndCategory> createCategoies(){
        List<SyndCategory> categories = new ArrayList<SyndCategory>();
        SyndCategory category = new SyndCategoryImpl();
        category.setName("MyProject");
        categories.add(category);
        return categories;
    }

    public static void saveRssFeed(SyndFeed rssFeed)  {
        String feedFileName = rssFeed.getLink();
        feedFileName = feedFileName.replaceAll("/", "~").replaceAll("https:","");
        Writer writer = null;
        try {
            writer = new FileWriter("feedFiles/" + feedFileName + ".xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            SyndFeedOutput output = new SyndFeedOutput();
            try {
                output.output(rssFeed,writer);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FeedException e) {
                e.printStackTrace();
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
