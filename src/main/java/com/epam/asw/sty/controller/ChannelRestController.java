package com.epam.asw.sty.controller;


import com.epam.asw.sty.model.RssChannel;
import com.epam.asw.sty.service.channel.ChannelStatsService;
import com.epam.asw.sty.service.channel.ChannelService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ChannelRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="channelServiceImpl")
    private ChannelService channelService;


    //-------------------Retrieve All Channels--------------------------------------------------------

    @RequestMapping(value = "/channel/", method = RequestMethod.GET)
    public ResponseEntity<List<RssChannel>> listAllChannels(Principal user) {

        List<RssChannel> rssChannels;
        String currentUser = user.getName();
        String logDebugMessage = "Getting channel for user: " + currentUser;
        logger.debug("{}.", logDebugMessage);
        if (!("admin").equals(currentUser)) {
            rssChannels = channelService.findByUser(currentUser);
        } else {
            rssChannels = channelService.populateChannelsFromDB();
        }
        String msg = "There are " + rssChannels.size() + " rssChannels found for user: " + currentUser;
        logger.info("{}  - {}.", msg, rssChannels);
        if(rssChannels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(rssChannels, HttpStatus.OK);
    }

    //-------------------Retrieve Channels For Current User--------------------------------------------------------


    @RequestMapping(value = "/channel/my", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getChannelForCurrentUser(ModelAndView model, Principal user) {

        String currentUser = user.getName();
        String logDebugMessage = "Getting rssChannel for user: " + currentUser;
        logger.debug("{}.", logDebugMessage);
        List<RssChannel> rssChannel = channelService.findByUser(currentUser);
        logger.info("{}.", rssChannel);
        String msg = "There are " + rssChannel.size() + " channels found for user: " + currentUser;
        model.addObject("message", msg);
        model.addObject("rssChannel", rssChannel);
        return model;

    }

    //-------------------Retrieve Channels For Specific User--------------------------------------------------------


    @RequestMapping(value = "/user={currentUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Model> getChannelForSpecificUser(@PathVariable("currentUser") String currentUser, Model model, Principal user) {

        String logDebugMessage = "Getting rssChannel for user: " + currentUser;
        logger.debug("{}.", logDebugMessage);
        List<RssChannel> rssChannel = channelService.findByUser(currentUser);
        logger.info("{}.", rssChannel);
        String msg = "There are " + rssChannel.size() + " channels found for user: " + currentUser;
        model.addAttribute("message", msg);
        model.addAttribute("rssChannel", rssChannel);
        return new ResponseEntity<>(model, HttpStatus.OK);

    }

    //-------------------Retrieve Single RssChannel--------------------------------------------------------

    @RequestMapping(value = "/channel/{shortid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RssChannel> getChannelbyID(@PathVariable("shortid") long shortid) {

        String logDebugMessage = "Fetching RssChannel with id " + shortid;
        logger.debug("{}.", logDebugMessage);
        RssChannel rssChannel = channelService.findByShortID(shortid);
        if (rssChannel == null) {
            logDebugMessage = "RssChannel with id " + shortid + " not found";
            logger.debug("{}.", logDebugMessage);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rssChannel, HttpStatus.OK);
    }



    //-------------------Create Single RssChannel--------------------------------------------------------

    @RequestMapping(value = "/channel/", method = RequestMethod.POST)
    public ResponseEntity<Void> createChannel(@RequestBody RssChannel rssChannel, UriComponentsBuilder ucBuilder, Model model, Principal user) {
        logger.info("Creating RssChannel with url: " + rssChannel.getLink());

        HttpHeaders headers = new HttpHeaders();
        if (channelService.isChannelExist(rssChannel)) {
            final String msg = "RssChannel with link " + rssChannel.getLink() + " already exist";
            logger.info(msg);
            model.addAttribute("msg", msg);
            headers.setLocation(ucBuilder.path("/alreadyExist").buildAndExpand(rssChannel.getId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }
        rssChannel.setUser(user.getName());
        channelService.saveChannel(rssChannel, user.getName());


        headers.setLocation(ucBuilder.path("/rssChannel/{id}").buildAndExpand(rssChannel.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }



    //------------------- Update Single RssChannel --------------------------------------------------------

    @RequestMapping(value = "/channel/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RssChannel> updateChannel(@PathVariable("id") String  id, @RequestBody RssChannel rssChannel) {

        String logDebugMessage = "Updating RssChannel " + id;
        logger.debug("{}.", logDebugMessage);
        RssChannel currentRssChannel = channelService.findById(id);

        if (currentRssChannel ==null) {
            logDebugMessage = "RssChannel with id " + id + " not found";
            logger.debug("{}.", logDebugMessage);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentRssChannel.setUser(rssChannel.getUser());
        currentRssChannel.setDescription(rssChannel.getDescription());
        currentRssChannel.setLink(rssChannel.getLink());
        currentRssChannel.setDescription(rssChannel.getDescription());
        currentRssChannel.setPubDate(rssChannel.getPubDate());
        currentRssChannel.setLanguage(rssChannel.getLanguage());

        channelService.updateChannel(currentRssChannel);
        return new ResponseEntity<>(currentRssChannel, HttpStatus.OK);
    }




    //------------------- Delete Single RssChannel --------------------------------------------------------

    @RequestMapping(value = "/channel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RssChannel> deleteChannel(@PathVariable("id") String id) {

        String logDebugMessage = "Fetching & Deleting RssChannel with id " + id;
        logger.debug("{}.", logDebugMessage);
        RssChannel rssChannel = channelService.findById(id);
        if (rssChannel == null) {
            logDebugMessage = "Unable to delete. RssChannel with id " + id + " not found";
            logger.debug("{}.", logDebugMessage);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        channelService.deleteChannelById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //------------------- Delete All Channels --------------------------------------------------------

    @RequestMapping(value = "/channel/", method = RequestMethod.DELETE)
    public ResponseEntity<RssChannel> deleteAllChannels() {

        String logDebugMessage = "Deleting All Channels";
        logger.debug("{}.", logDebugMessage);
        channelService.deleteAllChannels();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //-------------------Retrieve Channels Statistic Data--------------------------------------------------------

    @RequestMapping(value = "/channel/stats", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> DBChannelsStats(Principal user) {

        List<RssChannel> rssChannels = channelService.populateChannelsFromDB();
        if(rssChannels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        else {
            ChannelStatsService statsTest = new ChannelStatsService();
            Map<String,Object> stats = new java.util.HashMap<>();
            stats.put("DB RssChannel count: ", rssChannels.size());
            stats.put("DB RssChannel count per user: ", statsTest.channelsPerUser(rssChannels, user.getName()));
            return new ResponseEntity<>(stats, HttpStatus.OK);
        }
    }

}
