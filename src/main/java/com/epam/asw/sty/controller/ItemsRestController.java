package com.epam.asw.sty.controller;


import com.epam.asw.sty.model.Item;
import com.epam.asw.sty.service.item.ItemService;
import com.epam.asw.sty.service.rss.RSSfeedSavertoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
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

    //-------------------Retrieve Items For Channel by Link--------------------------------------------------------

    @RequestMapping(value = "/item/{shortid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChannelForUser(@PathVariable("shortid") long shortid, Model model) {

        String logDebugMessage = "Getting items for channel with shotr ID" + shortid;
        logger.debug("{}.", logDebugMessage);
        List<Item> items = itemService.findByChannelLink(shortid);
        logger.info("{}.",  items);
        model.addAttribute("channel", items);
        return "dbItemViewPage";

    }







}
