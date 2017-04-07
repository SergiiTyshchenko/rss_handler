package com.epam.asw.sty.service.item;

import com.epam.asw.sty.dao.ItemDao;
import com.epam.asw.sty.model.Item;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        List<Item> items = itemDao.findByChannelID(channelID);
/*        List<Item> items = new ArrayList<Item>();
        for(Item item : populateItemsFromDB()){
            if(item.getChannelID() == channelID){
                items.add(item);
            }
        }*/
        return items;
    }

    public List<Item> findItemsForUserByChannelID(long shortid, String user,  int count) {
        List<Item> items = itemDao.findForUserByChannelID(shortid, user, count);
        return items;
    }

    public void saveItem(Item item) {

        item.setId(new Item().getId());
        itemDao.insertNewEntry(item);
    }


    public void convertSyndEntryToItem(List<SyndEntryImpl>items, long shortid) {
        for (SyndEntryImpl item: items) {
            Item customizedItem = new Item();
            customizedItem.setChannelID(shortid);
            customizedItem.setPubDate(item.getPublishedDate());
            Description itemDescription = new Description();
            itemDescription.setValue(item.getDescription().getValue());
            customizedItem.setDescription(itemDescription);
            customizedItem.setTitle(item.getTitle());
            customizedItem.setLink(item.getLink());
            saveItem(customizedItem);
        }

    }
    public void deleteItemByChannelID(long  shortid) {

/*        items = populateItemsFromDB();
        for (Iterator<Item> iterator = items.iterator(); iterator.hasNext(); ) {
            Item item = iterator.next();
            if (item.getChannelID() == id) {
                itemDao.removeEntryByChannelID(id);
                break;
            }
        }*/

        List<Item> items = itemDao.findByChannelID(shortid);
        for (Item item: items){
            itemDao.removeEntryByChannelID(shortid);
        }
    }

    public List<Item> findItemsForUserByCountSortedByDate(String user, int count, String orderItemField, long shortid) {
        List<Item> items = itemDao.findForUserByCountSortedByDate(user, count, orderItemField,  shortid);
        return items;
    }

    public List<Item> findItemsForUserbyChannelByCountSortedbyTitle(String user, int count, String orderItemField) {
        List<Item> items = itemDao.findForUserbyChannelByCountSortedbyTitle(user, count, orderItemField);
        return items;
    }











}