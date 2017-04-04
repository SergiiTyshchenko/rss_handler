
package com.epam.asw.sty.service.item;



import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;
import com.sun.syndication.feed.synd.SyndEntryImpl;

import java.util.List;


public interface ItemService {



    List<Item> findByChannel(long  channelID);

    List<Item> findAllItems();

    List<Item> findItemsForUserByCountSortedByDate(String user, int count, String orderItemField, long shortid);

    List<Item> findItemsForUserbyChannelByCountSortedbyTitle(String user, int count, String orderItemField);

    List<Item> findItemsForUserByChannelID(long shortid, String user,  int count);

    List<Item> populateItemsFromDB();

    void saveItem(Item item);

    public void convertSyndEntryToItem(List<SyndEntryImpl>items, long shortid);

    void deleteItemByChannelID(long id);

}