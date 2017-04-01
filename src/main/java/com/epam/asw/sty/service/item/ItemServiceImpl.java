package com.epam.asw.sty.service.item;

import com.epam.asw.sty.dao.ChannelDao;
import com.epam.asw.sty.dao.ItemDao;
import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;
import com.epam.asw.sty.service.channel.ChannelRulesChecker;
import com.epam.asw.sty.service.channel.ChannelService;
import com.epam.asw.sty.service.rss.RSSFeedReader;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("itemServiceImpl")
@Transactional
public class ItemServiceImpl implements ItemService {


    @Resource(name="itemDaoImpl")
    ItemDao itemDao;

    private static final AtomicLong counter = new AtomicLong();

    private List<Item> items;

    public ItemServiceImpl() {
        this.items = new ArrayList<Item>();
    }


    public List<Item> findAllItems() {
        return populateItemsFromDB();
    }

    public List<Item> populateItemsFromDB(){
        List<Item> items = itemDao.findAll();
        return items;
    }


    public List<Item> findByChannel(long  channelID) {
        List<Item> items = new ArrayList<Item>();
        for(Item item : populateItemsFromDB()){
            //if(item.getChannelID().equals(channelID)){
            if(item.getChannelID() == channelID){
                items.add(item);
            }
        }
        return items;
    }

    public List<Item> findItemsForUserByChannelID(long shortid, String user) {
        List<Item> items = itemDao.findForUserByChannelID(shortid, user);
        return items;
    }

    public void saveItem(Item item) {

        item.setId(new Item().getId());
     //   items = populateItemsFromDB();
/*        if (items.size() == 0) {
            counter.set(0);
        } else {
            counter.set(items.get(items.size() - 1).getId());
        }
        item.setId((int) counter.incrementAndGet());*/
        itemDao.insertNewEntry(item);
    }

    public void deleteItemByChannelID(long  id) {

        items = populateItemsFromDB();
        for (Iterator<Item> iterator = items.iterator(); iterator.hasNext(); ) {
            Item item = iterator.next();
            //if (item.getChannelID().equals(id)) {
            if (item.getChannelID() == id) {
                itemDao.removeEntryByChannelID(id);
                break;
            }
        }
    }

    public List<Item> findItemsForUserByCountSortedByDate(String user, int count, String orderItemField) {
        List<Item> items = itemDao.findForUserByCountSortedByDate(user, count, orderItemField);
        return items;
    }

    public List<Item> findItemsForUserbyChannelByCountSortedbyTitle(long shortid, String user, int count, String orderItemField) {
        List<Item> items = itemDao.findForUserbyChannelByCountSortedbyTitle(shortid, user, count, orderItemField);
        return items;
    }











}