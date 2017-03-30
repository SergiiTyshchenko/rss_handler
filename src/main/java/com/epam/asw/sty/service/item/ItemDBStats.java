package com.epam.asw.sty.service.item;

import com.epam.asw.sty.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDBStats {


    public List<String> ItemsPerChannel(List<Item> items, long shortid){
        List<String> stats = new ArrayList<String>();
        Integer itemsCount=0;
        for (Item item : items){
            if (item.getChannelID() == shortid){
                itemsCount++;
            }
        }
        stats.add(0,"Items count per Channel with ID " + shortid  + " : " + itemsCount);
        return stats;
    }
}
