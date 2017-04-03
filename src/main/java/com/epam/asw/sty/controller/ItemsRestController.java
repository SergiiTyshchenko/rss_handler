package com.epam.asw.sty.controller;


import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;
import com.epam.asw.sty.service.channel.ChannelService;
import com.epam.asw.sty.service.item.ItemService;
import com.epam.asw.sty.service.rss.RSSfeedSavertoDB;
import org.apache.commons.lang3.StringEscapeUtils;
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

    private static final String ITEM_FOR_CHANNEL_VIEW = "itemsForChannelView";

    @Resource(name="itemServiceImpl")
    private ItemService itemService;

    @Resource(name="channelServiceImpl")
    private ChannelService channelService;


    //-------------------Retrieve Items by User For Channel by ChannelID--------------------------------------------------------


    @RequestMapping(value = "/item/channel={channelID}", method = RequestMethod.GET, params={"itemsCount"})
    public ResponseEntity<List<Item>> getChannelForUser(@RequestParam(value="itemsCount", required=false) int itemsCount,
                                                        @PathVariable("channelID") long channelID, Model model, Principal user) {

        String logDebugMessage ="";
        if (itemsCount == -1){
            int count = Integer.MAX_VALUE;
            logDebugMessage = "Getting items for user: " + user.getName() + " for channel with short ID " + channelID;
        } else{
            logDebugMessage = "Getting last " + itemsCount + " items for user " + user.getName() + " for channel with short ID " + channelID;
        }
        logger.debug("{}.", logDebugMessage);

        String orderItemField = "";
        if ((channelID == -1) && (itemsCount == -1)){
            orderItemField = "channelID";
        } else{
            orderItemField = "pubDate";
        }
        logDebugMessage = "Selected order is: " + orderItemField;
        logger.debug("{}.", logDebugMessage);

        List<Item> items = itemService.findItemsForUserByCountSortedByDate(user.getName(), itemsCount, orderItemField, channelID);
        String msg = "There are " + items.size() + " items for channel with short ID=" + channelID;
        logger.info("Items count {}.",  items.size());
        if(items.isEmpty()){
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

    }

    @RequestMapping(value="/itemsForChannel", method = RequestMethod.GET, params={"channelID"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getItemsForChannelIndexPage(@RequestParam(value="channelID", required=false) int channelID, Model model, Principal user) {

        String logDebugMessage = "Getting items for channel with short ID " + channelID;
        logger.debug("{}.", logDebugMessage);

        JSONObject json = new JSONObject();
        json.put("channelID", channelID);
        String formattedJson = StringEscapeUtils.escapeHtml4(json.toString());
        model.addAttribute("newItemJson", formattedJson);

        if (channelID!=-1) {
            Channel channel = channelService.findByShortID(channelID);
            model.addAttribute("channelTitle", channel.getTitle());
        } else {
            model.addAttribute("channelTitle", "All Channels");
        }

        return ITEM_FOR_CHANNEL_VIEW;
    }




}
