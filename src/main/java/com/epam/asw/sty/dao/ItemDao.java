package com.epam.asw.sty.dao;



import com.epam.asw.sty.model.RssItem;

import java.util.List;

public interface ItemDao {

	List<RssItem> findByChannelID(long shortid);
	
	List<RssItem> findAll();


	List<RssItem> findLimitedItemsForOneChannelSortedByPubDate(String user, int count, long shortid);

	List<RssItem> findForUserbyChannelByCountSortedbyTitle(String user, int count, String orderItemField);

	List<RssItem> findAllItemsForOneChannelSortedByChannleID(String user, long shortid);

	List<RssItem> findLimitedItemsForAllChannelsSortedByPubDate(String user, int count);

	List<RssItem> findAllItemsForAllChannelsSortedByChannelID(String user);

	List<RssItem> findForUserByChannelID(long shortid, String user, int count);

	Object insertNewEntry(RssItem items);

	Object removeEntryByChannelID(long shortid);

}





