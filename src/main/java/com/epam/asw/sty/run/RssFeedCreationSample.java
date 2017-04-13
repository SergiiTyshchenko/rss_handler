package com.epam.asw.sty.run;


import com.epam.asw.sty.service.rss.RssFeedReader;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RssFeedCreationSample {

    private final static Logger logger = LoggerFactory.getLogger(RssFeedCreationSample.class);

    public static void main(String[] args)  {

        //KB
        //http://www.javaworld.com/article/2077795/java-se/manage-rss-feeds-with-the-rome-api.html?page=2

        String url = "https://dou.ua/feed/";
        RssFeedReader RssFeedReader = new RssFeedReader(url);
        try {
            saveRssFeed(RssFeedReader.obtainRSSFeed(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static SyndFeed createFeed(){
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_1.0");
        feed.setTitle("MyProject Build Results");
        feed.setLink("http://myproject.mycompany.com/continuum");
        feed.setDescription("Continuous build results for the MyProject project");
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

    private static SyndContent createDescription(){
        SyndContent description = new SyndContentImpl();
        description.setType("text/html");
        description.setValue("The build was successful!");

        return description;
    }

    private static  List<SyndCategory> createCategoies(){
        List<SyndCategory> categories = new ArrayList<SyndCategory>();
        SyndCategory category = new SyndCategoryImpl();
        category.setName("MyProject");
        categories.add(category);
        return categories;
    }

    private static void saveRssFeed(SyndFeed rssFeed) throws IOException {


        String feedFileName = rssFeed.getLink();
        feedFileName = feedFileName.replaceAll("/", "~").replaceAll("https:","");
        final File file = new File("feedFiles/" + feedFileName + ".xml");
        Writer writer = null;
        try {
            writer = new FileWriter(file);
            SyndFeedOutput output = new SyndFeedOutput();
            try {
                output.output(rssFeed, writer);
            } catch (IOException e) {
                throw new IOException("IOException when try to create SyndFeedOutput", e);
            } catch (FeedException e) {
                logger.error("Exception raised", e);
            }
        } catch (IOException e) {
            throw new IOException("IOException when try to create writer for file " + file.getName() + " to a path "
                    + file.getPath(), e);
        }
        finally {
            IOUtils.closeQuietly(writer);
        }
    }


}
