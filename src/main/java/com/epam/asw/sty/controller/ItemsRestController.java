package com.epam.asw.sty.controller;


import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;
import com.epam.asw.sty.service.channel.ChannelDBtStats;
import com.epam.asw.sty.service.channel.ChannelService;
import com.epam.asw.sty.service.item.ItemService;
import com.epam.asw.sty.service.rss.RSSFeedReader;
import com.epam.asw.sty.service.rss.RSSFeedSavertoDB;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ItemsRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="itemServiceImpl")
    private ItemService itemService;


    //-------------------Retrieve All Items--------------------------------------------------------

    @RequestMapping(value = "/item/", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> listAllItems() {
        List<Item> items = itemService.populateItemsFromDB();
        if(items.isEmpty()){
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    //-------------------Retrieve Items For Channel--------------------------------------------------------

    @ResponseBody
    @RequestMapping(value = "/item/channelID={channelID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChannelForUser(@PathVariable("user") long channelID, Model model) {

        String logDebugMessage = "Getting items for channel " + channelID;
        logger.debug("{}.", logDebugMessage);
        List<Item> items = itemService.findByChannel(channelID);
        logger.info("{}.",  items);
        model.addAttribute("channel", items);
        return "dbItemViewPage";

    }







}
