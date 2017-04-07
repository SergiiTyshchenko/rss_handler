package com.epam.asw.sty.dao;



import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;

import java.util.List;

public interface ItemDao {

	List<Item> findByChannelID(long shortid);
	
	List<Item> findAll();

	List<Item> findForUserByCountSortedByDate(String user, int count, String orderItemField, long shortid);

	List<Item> findForUserbyChannelByCountSortedbyTitle(String user, int count, String orderItemField);

	List<Item> findForUserByChannelID(long shortid, String user,  int count);

	Object insertNewEntry(Item items);

	Object removeEntryByChannelID(long shortid);

}





