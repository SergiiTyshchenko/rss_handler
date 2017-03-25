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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ChannelRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="channelServiceImpl")
    private ChannelService channelService;


    //-------------------Retrieve All Channels--------------------------------------------------------

    @RequestMapping(value = "/channel/", method = RequestMethod.GET)
    public ResponseEntity<List<Channel>> listAllChannelss() {
        List<Channel> channels = channelService.populateChannelsFromDB();
        if(channels.isEmpty()){
            return new ResponseEntity<List<Channel>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Channel>>(channels, HttpStatus.OK);
    }

    //-------------------Retrieve Channels For User--------------------------------------------------------

    @ResponseBody
    @RequestMapping(value = "/user={user}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getChannelForUser(@PathVariable("user") String user, Model model) {

        String logDebugMessage = "Getting channel for user " + user;
        logger.debug("{}.", logDebugMessage);
        List<Channel> channel = channelService.findByUser(user);
        logger.info("{}.",  channel);
        model.addAttribute("channel", channel);
        return "dbChannelViewPage";

    }
    //-------------------Retrieve Single Channel--------------------------------------------------------

    @RequestMapping(value = "/channel/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> getChannelbyID(@PathVariable("id") String id) {
        String logDebugMessage = "Fetching Channel with id " + id;
        logger.debug("DEBUG message {}.", logDebugMessage);
        Channel channel = channelService.findById(id);
        if (channel == null) {
            logDebugMessage = "Channel with id " + id + " not found";
            logger.debug("DEBUG message {}.", logDebugMessage);
            return new ResponseEntity<Channel>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Channel>(channel, HttpStatus.OK);
    }



    //-------------------Create Single Channel--------------------------------------------------------

    @RequestMapping(value = "/channel/", method = RequestMethod.POST)
    public ResponseEntity<Void> createChannel(@RequestBody Channel channel, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Channel with url: " + channel.getLink());

        if (channelService.isChannelExist(channel)) {
            System.out.println("Channel with link " + channel.getLink() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        channelService.saveChannel(channel);

        HttpHeaders headers = new HttpHeaders();
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
    public ResponseEntity<Map<String,Object>> DBChannelsStats() {
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
            channel_count_per_user=statsTest.ChannelsPerUser(channels);
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
