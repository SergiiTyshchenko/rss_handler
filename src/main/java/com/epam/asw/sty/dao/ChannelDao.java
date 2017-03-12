package com.epam.asw.sty.dao;

import com.epam.asw.sty.model.Channel;
import com.sun.syndication.feed.synd.SyndFeed;

import java.util.List;

public interface ChannelDao {

	List<Channel> findByTitle(String title);
	
	List<Channel> findAll();

	Object insertNewEntry(String title);

	Object insertNewSiteEntry(SyndFeed rssfeed);
}