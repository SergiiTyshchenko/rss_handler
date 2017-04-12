package com.epam.asw.sty.controller;


import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;
import com.epam.asw.sty.service.channel.ChannelService;
import com.epam.asw.sty.service.item.ItemService;
import org.apache.commons.lang3.StringEscapeUtils;
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

@Controller
public class ItemsRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ITEM_FOR_CHANNEL_VIEW = "itemsForChannelView";

    @Resource(name="itemServiceImpl")
    private ItemService itemService;

    @Resource(name="channelServiceImpl")
    private ChannelService channelService;


    //-------------------Show JSP With All Items For Specific Channel --------------------------------------------------------

    @RequestMapping(value="/itemsForChannel", method = RequestMethod.GET, params={"channelID"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllItemsForChannelPage(@RequestParam(value="channelID", required=false) int channelID, Model model) {

        String logDebugMessage = "Getting items for channel with short ID " + channelID;
        logger.debug("{}.", logDebugMessage);
        JSONObject json = new JSONObject();
        json.put("channelID", channelID);
        String formattedJson = StringEscapeUtils.escapeHtml4(json.toString());
        model.addAttribute("newItemJson", formattedJson);
        Channel channel = channelService.findByShortID(channelID);
        model.addAttribute("channelTitle", channel.getTitle());

        return ITEM_FOR_CHANNEL_VIEW;
    }

    //-------------------Show JSP With All Items For All Channels --------------------------------------------------------

    @RequestMapping(value="/itemsForChannel/allChannels", method = RequestMethod.GET)
    public String getAllItemsForAllChannelsPage(Model model) {

        String logDebugMessage = "Getting items for All Channels";
        logger.debug("{}.", logDebugMessage);
        model.addAttribute("channelTitle", "All Channels");

        return ITEM_FOR_CHANNEL_VIEW;
    }

    //-------------------Retrieve All Items For All Channels For Logged User --------------------------------------------------

    @RequestMapping(value = "/item/allChannels", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getAllItemsForAllChannelsForUserPage(Principal user) {

        String logDebugMessage = "Getting All items for user: " + user.getName() + " for All channels";
        logger.debug("{}.", logDebugMessage);
        List<Item> items = itemService.findAllItemsForAllChannelsSortedByChannelID(user.getName());
        return getResponseEntity(items);
    }

    //-------------------Retrieve All Items For Specific Channel For Logged User --------------------------------------------------

    @RequestMapping(value = "/item/channel={channelID}", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getAllItemsForChannelForUserPage(
                                        @PathVariable("channelID") long channelID, Principal user) {

        String logDebugMessage = "Getting All items for user: " + user.getName() + " for channel with short ID " + channelID;
        logger.debug("{}.", logDebugMessage);
        List<Item> items = itemService.findAllItemsForOneChannelSortedByChannelID(user.getName(), channelID);
        return getResponseEntity(items);
    }

    //-------------------Retrieve Limited Items For All Channels For Logged User --------------------------------------------------

    @RequestMapping(value = "/item/allChannels", method = RequestMethod.GET, params={"itemsCount"})
    public ResponseEntity<List<Item>> getLimitedItemsForAllChannelsForUserPage(
                                        @RequestParam(value="itemsCount", required=false) int itemsCount, Principal user) {

        String logDebugMessage = "Getting last " + itemsCount + " items for user " + user.getName() + " for All channels";
        logger.debug("{}.", logDebugMessage);
        List<Item> items = itemService.findLimitedItemsForAllChannelsSortedByPubDate(user.getName(), itemsCount);
        return getResponseEntity(items);
    }

    //-------------------Retrieve Limited Items For Specific Channel For Logged User --------------------------------------------------

    @RequestMapping(value = "/item/channel={channelID}", method = RequestMethod.GET, params={"itemsCount"})
    public ResponseEntity<List<Item>> getLimitedItemsForChannelForUserPage(
                                        @RequestParam(value="itemsCount", required=false) int itemsCount,
                                        @PathVariable("channelID") long channelID, Principal user) {

        String logDebugMessage = "Getting last " + itemsCount + " items for user " + user.getName() + " for channel with short ID " + channelID;
        logger.debug("{}.", logDebugMessage);
        List<Item> items = itemService.findLimitedItemsForOneChannelSortedByPubDate(user.getName(), itemsCount, channelID);
        return getResponseEntity(items);
    }


    public ResponseEntity<List<Item>> getResponseEntity (List<Item> items) {
        logger.info("Items count {}.",  items.size());
        if(items.isEmpty()){
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

}
