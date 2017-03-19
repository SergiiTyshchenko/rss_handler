package com.epam.asw.sty.dao;



import com.epam.asw.sty.model.Channel;
import com.sun.syndication.feed.synd.SyndFeed;

import java.util.List;

public interface ChannelDao {

	List<Channel> findByUser(String user);
	
	List<Channel> findAll();

	Object insertNewEntry(Channel channel);

	Object removeEntryByID(long id);

	Object insertNewSiteEntry(SyndFeed rssfeed);

	Object updateEntry(Channel channel);
}





