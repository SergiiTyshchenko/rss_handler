package com.epam.asw.sty.service.item;

import com.epam.asw.sty.model.RssItem;

import java.util.ArrayList;
import java.util.List;

public class ItemDBStats {


    public List<String> itemsPerChannel(List<RssItem> rssItems, long shortid){
        List<String> stats = new ArrayList<>();
        Integer itemsCount=0;
        for (RssItem rssItem : rssItems){
            if (rssItem.getChannelID() == shortid){
                itemsCount++;
            }
        }
        stats.add(0,"Items count per RssChannel with ID " + shortid  + " : " + itemsCount);
        return stats;
    }
}
