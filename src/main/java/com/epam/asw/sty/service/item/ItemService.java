
package com.epam.asw.sty.service.item;



import com.epam.asw.sty.model.RssItem;
import com.sun.syndication.feed.synd.SyndEntryImpl;

import java.util.List;


public interface ItemService {



    List<RssItem> findByChannel(long  channelID);

    List<RssItem> findAllItems();


    List<RssItem> findLimitedItemsForOneChannelSortedByPubDate(String user, int count, long shortid);

    List<RssItem> findAllItemsForAllChannelsSortedByChannelID(String user);

    List<RssItem> findAllItemsForOneChannelSortedByChannelID(String user, long shortid);

    List<RssItem> findLimitedItemsForAllChannelsSortedByPubDate(String user, int count);

    List<RssItem> findItemsForUserbyChannelByCountSortedbyTitle(String user, int count, String orderItemField);

    List<RssItem> findItemsForUserByChannelID(long shortid, String user, int count);

    List<RssItem> populateItemsFromDB();

    void saveItem(RssItem rssItem);

    public void convertSyndEntryToItem(List<SyndEntryImpl>items, long shortid);

    void deleteItemByChannelID(long id);

}