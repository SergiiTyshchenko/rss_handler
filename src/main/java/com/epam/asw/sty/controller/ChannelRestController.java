package com.epam.asw.sty.controller;


import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.service.channel.ChannelDBStats;
import com.epam.asw.sty.service.channel.ChannelService;

import com.epam.asw.sty.service.rss.RSSFeedReader;
import com.epam.asw.sty.service.rss.RSSfeedSavertoDB;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.IOException;
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
    public ResponseEntity<List<Channel>> listAllChannels(Principal user) {
        List<Channel> channels = new ArrayList<Channel>();
        String currentUser = user.getName();
        String logDebugMessage = "Getting channel for user: " + currentUser;
        logger.debug("{}.", logDebugMessage);
        if (!currentUser.equals("admin")) {
            channels = channelService.findByUser(currentUser);
        } else {
            channels = channelService.populateChannelsFromDB();
        }
        logger.info("{}.",  channels);
        String msg = "There are " + channels.size() + " channels found for user: " + currentUser;
        if(channels.isEmpty()){
            return new ResponseEntity<List<Channel>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Channel>>(channels, HttpStatus.OK);
    }

    //-------------------Retrieve Channels For Current User--------------------------------------------------------


    @RequestMapping(value = "/channel/my", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChannelForCurrentUser(Model model, Principal user) {

        String currentUser = user.getName();
        String logDebugMessage = "Getting channel for user: " + currentUser;
        logger.debug("{}.", logDebugMessage);
        List<Channel> channel = channelService.findByUser(currentUser);
        logger.info("{}.",  channel);
        String msg = "There are " + channel.size() + " channels found for user: " + currentUser;
        model.addAttribute("message", msg);
        model.addAttribute("channel", channel);
        return "dbChannelViewPage";

    }

    //-------------------Retrieve Channels For Specific User--------------------------------------------------------


    @RequestMapping(value = "/user={currentUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChannelForSpecificUser(@PathVariable("currentUser") String currentUser, Model model, Principal user) {

        String logDebugMessage = "Getting channel for user: " + currentUser;
        logger.debug("{}.", logDebugMessage);
        List<Channel> channel = channelService.findByUser(currentUser);
        logger.info("{}.",  channel);
        String msg = "There are " + channel.size() + " channels found for user: " + currentUser;
        model.addAttribute("message", msg);
        model.addAttribute("channel", channel);
        return "dbChannelViewPage";

    }

    //-------------------Retrieve Single Channel--------------------------------------------------------

    @RequestMapping(value = "/channel/{shortid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> getChannelbyID(@PathVariable("shortid") long shortid) {
        String logDebugMessage = "Fetching Channel with id " + shortid;
        logger.debug("DEBUG message {}.", logDebugMessage);
        Channel channel = channelService.findByShortID(shortid);
        if (channel == null) {
            logDebugMessage = "Channel with id " + shortid + " not found";
            logger.debug("DEBUG message {}.", logDebugMessage);
            return new ResponseEntity<Channel>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Channel>(channel, HttpStatus.OK);
    }



    //-------------------Create Single Channel--------------------------------------------------------

    @RequestMapping(value = "/channel/", method = RequestMethod.POST)
    public ResponseEntity<Void> createChannel(@RequestBody Channel channel, UriComponentsBuilder ucBuilder, Model model, Principal user) {
        logger.info("Creating Channel with url: " + channel.getLink());

        HttpHeaders headers = new HttpHeaders();

        if (channelService.isChannelExist(channel)) {
            final String msg = "Channel with link " + channel.getLink() + " already exist";
            logger.info(msg);
            model.addAttribute("msg", msg);
            headers.setLocation(ucBuilder.path("/alreadyExist").buildAndExpand(channel.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CONFLICT);
        }
        channel.setUser(user.getName());
        channelService.saveChannel(channel);


        headers.setLocation(ucBuilder.path("/channel/{id}").buildAndExpand(channel.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }



    //------------------- Update Single Channel --------------------------------------------------------

    @RequestMapping(value = "/channel/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Channel> updateChannel(@PathVariable("id") String  id, @RequestBody Channel channel) {
        String logDebugMessage = "Updating Channel " + id;
        logger.debug("DEBUG message {}.", logDebugMessage);
        Channel currentChannel = channelService.findById(id);

        if (currentChannel==null) {
            logDebugMessage = "Channel with id " + id + " not found";
            logger.debug("DEBUG message {}.", logDebugMessage);
            return new ResponseEntity<Channel>(HttpStatus.NOT_FOUND);
        }

        currentChannel.setUser(channel.getUser());
        currentChannel.setDescription(channel.getDescription());
        currentChannel.setLink(channel.getLink());
        currentChannel.setDescription(channel.getDescription());
        currentChannel.setPubDate(channel.getPubDate());
        currentChannel.setLanguage(channel.getLanguage());

        channelService.updateChannel(currentChannel);
        return new ResponseEntity<Channel>(currentChannel, HttpStatus.OK);
    }




    //------------------- Delete Single Channel --------------------------------------------------------

    @RequestMapping(value = "/channel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Channel> deleteChannel(@PathVariable("id") String id) {
        String logDebugMessage = "Fetching & Deleting Channel with id " + id;
        logger.debug("DEBUG message {}.", logDebugMessage);
        Channel channel = channelService.findById(id);
        if (channel == null) {
            logDebugMessage = "Unable to delete. Channel with id " + id + " not found";
            logger.debug("DEBUG message {}.", logDebugMessage);
            return new ResponseEntity<Channel>(HttpStatus.NOT_FOUND);
        }


        channelService.deleteChannelById(id);
        return new ResponseEntity<Channel>(HttpStatus.NO_CONTENT);
    }



    //------------------- Delete All Channels --------------------------------------------------------

    @RequestMapping(value = "/channel/", method = RequestMethod.DELETE)
    public ResponseEntity<Channel> deleteAllChannels() {
        String logDebugMessage = "Deleting All Channels";
        logger.debug("DEBUG message {}.", logDebugMessage);
        channelService.deleteAllChannels();
        return new ResponseEntity<Channel>(HttpStatus.NO_CONTENT);
    }


    //-------------------Retrieve Channels Statistic Data--------------------------------------------------------

    @RequestMapping(value = "/channel/stats", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> DBChannelsStats(Principal user) {
        List<Channel> channels = channelService.populateChannelsFromDB();
        Integer channels_count;
        if(channels.isEmpty()){
            return new ResponseEntity<Map<String,Object>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        else {
            ChannelDBStats statsTest = new ChannelDBStats();
            Map<String,Object> stats = new java.util.HashMap<>();
            channels_count = statsTest.DBChannelCount(channels);
            stats.put("DB Channel count: ", channels_count);
            List<String> channel_count_per_user = new ArrayList<String>();
            channel_count_per_user=statsTest.ChannelsPerUser(channels, user.getName());
            stats.put("DB Channel count per user: ", channel_count_per_user);
            return new ResponseEntity<Map<String,Object>>(stats, HttpStatus.OK);
        }
    }

    //-------------------Create Single Channel from Web--------------------------------------------------------


	@RequestMapping(value = "/channel/insert", method = RequestMethod.GET)
	public ResponseEntity<Object> DBchannelInsert() throws IOException, FeedException {
		String url = "https://dou.ua/feed/";
		RSSFeedReader RSSFeedReader = new RSSFeedReader(url);
		RSSfeedSavertoDB RSSFeedSavertoDB = new RSSfeedSavertoDB();
		SyndFeed rssFeed = RSSFeedReader.obtainRSSFeed(url);
		Object result = RSSFeedSavertoDB.saveRssFeedtoDB(rssFeed);
		//Object result = null;
		if(result==null){
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

}
