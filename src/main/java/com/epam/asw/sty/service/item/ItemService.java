
package com.epam.asw.sty.service.item;



import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;

import java.util.List;


public interface ItemService {



    List<Item> findByChannel(String  channelID);

    List<Item> findAllItems();

    List<Item> populateItemsFromDB();

    void saveItem(Item item);

    void deleteItemByChannelID(String id);

}