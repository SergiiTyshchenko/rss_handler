package com.epam.asw.sty.dao;



import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;

import java.util.List;

public interface ItemDao {

	List<Item> findByChannel(Channel channel);
	
	List<Item> findAll();

	Object insertNewEntry(Item items);

	Object removeEntryByChannelID(long id);

}





