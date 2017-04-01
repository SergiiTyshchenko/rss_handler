
package com.epam.asw.sty.service.item;



import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;

import java.util.List;


public interface ItemService {



    List<Item> findByChannel(long  channelID);

    List<Item> findAllItems();

    List<Item> findItemsForUserByCountSortedByDate(String user, int count, String orderItemField);

    List<Item> findItemsForUserbyChannelByCountSortedbyTitle(long shortid, String user, int count, String orderItemField);

    List<Item> findItemsForUserByChannelID(long shortid, String user);

    List<Item> populateItemsFromDB();

    void saveItem(Item item);

    void deleteItemByChannelID(long id);

}