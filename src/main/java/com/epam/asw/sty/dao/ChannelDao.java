package com.epam.asw.sty.dao;



import com.epam.asw.sty.model.Channel;
import com.sun.syndication.feed.synd.SyndFeed;

import java.util.List;
import java.util.UUID;

public interface ChannelDao {

	List<Channel> findByUser(String user);

	Channel findByLink(String link);
	
	List<Channel> findAll();

	Channel findByShortID(long shortid);

	Channel findLastAddedChannel();

	Channel findByID(String id);

	Object insertNewEntry(Channel channel);

	Object removeEntryByID(String id);

	Object insertNewSiteEntry(SyndFeed rssfeed);

	Object updateEntry(Channel channel);
}





