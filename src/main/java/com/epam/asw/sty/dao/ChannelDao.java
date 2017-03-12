package com.epam.asw.sty.dao;

import com.epam.asw.sty.model.Channel;

import java.util.List;

public interface ChannelDao {

	List<Channel> findByTitle(String title);
	
	List<Channel> findAll();

	Object insertNewEntry(String title);

	Object insertNewSiteEntry(String title, String description, String link,
							  String language, String dc_date, String dc_language, String item);
}