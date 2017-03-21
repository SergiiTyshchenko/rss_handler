
package com.epam.asw.sty.service.item;



import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;

import java.util.List;


public interface ItemService {



    List<Item> findByChannel(long channelID);

    List<Item> findAllItems();

    List<Item> populateItemsFromDB();



}