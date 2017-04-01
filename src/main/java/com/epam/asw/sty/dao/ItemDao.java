package com.epam.asw.sty.dao;



import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;

import java.util.List;

public interface ItemDao {

	List<Item> findByChannel(Channel channel);
	
	List<Item> findAll();

	List<Item> findForUserByCountSortedByDate(String user, int count, String orderItemField);

	List<Item> findForUserbyChannelByCountSortedbyTitle(long shortid, String user, int count, String orderItemField);

	List<Item> findForUserByChannelID(long shortid, String user);

	Object insertNewEntry(Item items);

	Object removeEntryByChannelID(long id);

}





