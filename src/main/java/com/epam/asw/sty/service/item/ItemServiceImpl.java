package com.epam.asw.sty.service.item;

import com.epam.asw.sty.dao.ItemDao;
import com.epam.asw.sty.model.RssItem;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("itemServiceImpl")
@Transactional
public class ItemServiceImpl implements ItemService {


    @Resource(name="itemDaoImpl")
    ItemDao itemDao;

    private static final AtomicLong counter = new AtomicLong();


    public List<RssItem> findAllItems() {
        return populateItemsFromDB();
    }

    public List<RssItem> populateItemsFromDB(){
        List<RssItem> rssItems = itemDao.findAll();
        return rssItems;
    }


    public List<RssItem> findByChannel(long  channelID) {
        List<RssItem> rssItems = itemDao.findByChannelID(channelID);
        return rssItems;
    }

    public List<RssItem> findItemsForUserByChannelID(long shortid, String user, int count) {
        List<RssItem> rssItems = itemDao.findForUserByChannelID(shortid, user, count);
        return rssItems;
    }

    public void saveItem(RssItem rssItem) {

        rssItem.setId(new RssItem().getId());
        itemDao.insertNewEntry(rssItem);
    }


    public void convertSyndEntryToItem(List<SyndEntryImpl>items, long shortid) {
        for (SyndEntryImpl item: items) {
            RssItem customizedRssItem = new RssItem();
            customizedRssItem.setChannelID(shortid);
            customizedRssItem.setPubDate(item.getPublishedDate());
            Description itemDescription = new Description();
            itemDescription.setValue(item.getDescription().getValue());
            customizedRssItem.setDescription(itemDescription);
            customizedRssItem.setTitle(item.getTitle());
            customizedRssItem.setLink(item.getLink());
            saveItem(customizedRssItem);
        }

    }
    public void deleteItemByChannelID(long  shortid) {

        List<RssItem> rssItems = itemDao.findByChannelID(shortid);
        for (RssItem rssItem : rssItems){
            itemDao.removeEntryByChannelID(shortid);
        }
    }


    public List<RssItem> findAllItemsForAllChannelsSortedByChannelID(String user) {
        List<RssItem> rssItems = itemDao.findAllItemsForAllChannelsSortedByChannelID(user);
        return rssItems;
    }

    public List<RssItem> findItemsForUserbyChannelByCountSortedbyTitle(String user, int count, String orderItemField) {
        List<RssItem> rssItems = itemDao.findForUserbyChannelByCountSortedbyTitle(user, count, orderItemField);
        return rssItems;
    }

    public List<RssItem> findLimitedItemsForAllChannelsSortedByPubDate(String user, int count) {
        List<RssItem> rssItems = itemDao.findLimitedItemsForAllChannelsSortedByPubDate(user, count);
        return rssItems;
    }

    public List<RssItem> findAllItemsForOneChannelSortedByChannelID(String user, long shortid) {
        List<RssItem> rssItems = itemDao.findAllItemsForOneChannelSortedByChannleID(user, shortid);
        return rssItems;
    }

    public List<RssItem> findLimitedItemsForOneChannelSortedByPubDate(String user, int count, long shortid) {
        List<RssItem> rssItems = itemDao.findLimitedItemsForOneChannelSortedByPubDate(user, count, shortid);
        return rssItems;
    }



}