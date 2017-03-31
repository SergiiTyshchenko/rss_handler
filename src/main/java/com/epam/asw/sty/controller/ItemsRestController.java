package com.epam.asw.sty.controller;


import com.epam.asw.sty.model.Item;
import com.epam.asw.sty.service.item.ItemService;
import com.epam.asw.sty.service.rss.RSSfeedSavertoDB;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class ItemsRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="itemServiceImpl")
    private ItemService itemService;


    //-------------------Retrieve All Items for current user--------------------------------------------------------

    @RequestMapping(value = "/item/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> listAllItems(Principal user, Model model) {
        //List<Item> items = itemService.populateItemsFromDB();
        int count = Integer.MAX_VALUE;
        String logDebugMessage = "Getting all items for user: " + user.getName();
        logger.debug("{}.", logDebugMessage);
        List<Item> items = itemService.findItemsForUserByDate(user.getName(), count);
        logger.info("Total Items count {}.",  items.size());
        if(items.isEmpty()){
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }


    //-------------------Retrieve Limited Items sorted by pubDate for current user--------------------------------------------------------

    @RequestMapping(value = "/item/count={itemsCount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> listLimitedSortedItemsforUser(@PathVariable("itemsCount") int itemsCount, Principal user, Model model) {
        String logDebugMessage = "Getting last " + itemsCount + " items for user " + user.getName();
        logger.debug("{}.", logDebugMessage);
        List<Item> items = itemService.findItemsForUserByDate(user.getName(), itemsCount);
        logger.info("Received Items count {}.",  items.size());
        if(items.isEmpty()){
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    //-------------------Retrieve Items For Channel by Link--------------------------------------------------------


    @RequestMapping(value = "/item/channel={channelID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getChannelForUser(@PathVariable("channelID") long channelID, Model model, Principal user) {

        String logDebugMessage = "Getting items for user: " + user.getName() + " for channel with short ID " + channelID;
        logger.debug("{}.", logDebugMessage);
        List<Item> items = itemService.findItemsForUserByChannelID(channelID, user.getName());
        String msg = "There are " + items.size() + " items for channel with short ID=" + channelID;
        logger.info("Items count {}.",  items.size());
        model.addAttribute("channelTitle", items.get(0).getChannelTitle());
        model.addAttribute("message", msg);
        model.addAttribute("item", items);


        //return "dbItemViewPage";
        //return "dbItemViewNew";
        if(items.isEmpty()){
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

    }







}
