package com.epam.asw.sty.dao;



import com.epam.asw.sty.model.RssChannel;
import com.sun.syndication.feed.synd.SyndFeed;

import java.util.List;

public interface ChannelDao {

	List<RssChannel> findByUser(String user);

	RssChannel findByLink(String link);
	
	List<RssChannel> findAll();

	RssChannel findByShortID(long shortid);

	RssChannel findLastAddedChannel();

	RssChannel findByID(String id);

	Object insertNewEntry(RssChannel rssChannel);

	Object removeEntryByID(String id);

	Object insertNewSiteEntry(SyndFeed rssfeed);

	Object updateEntry(RssChannel rssChannel);
}





