package com.epam.asw.sty.controller;


import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.service.ChannelService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ChannelRestControllerCRUD {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="channelServiceImpl")
    private ChannelService channelService;  //Service which will do all data retrieval/manipulation work


    //-------------------Retrieve All Channels--------------------------------------------------------

    @RequestMapping(value = "/channel/", method = RequestMethod.GET)
    public ResponseEntity<List<Channel>> listAllChannelss() {
        List<Channel> channels = channelService.populateChannelsFromDB();
        if(channels.isEmpty()){
            return new ResponseEntity<List<Channel>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Channel>>(channels, HttpStatus.OK);
    }


    //-------------------Retrieve Single Channel--------------------------------------------------------

    @RequestMapping(value = "/channel/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> getChannelbyID(@PathVariable("id") long id) {
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



    //-------------------Create a Channel--------------------------------------------------------

    @RequestMapping(value = "/channel/", method = RequestMethod.POST)
    public ResponseEntity<Void> createChannel(@RequestBody Channel channel, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Channel " + channel.getUser());

/*        if (channelService.isChannelExist(channel)) {
            System.out.println("User " + channel.getUser() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }*/

        channelService.saveChannel(channel);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/channel/{id}").buildAndExpand(channel.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }



    //------------------- Update Channel --------------------------------------------------------

    @RequestMapping(value = "/channel/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Channel> updateChannel(@PathVariable("id") long id, @RequestBody Channel channel) {
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



    //------------------- Delete a Channel --------------------------------------------------------

    @RequestMapping(value = "/channel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Channel> deleteChannel(@PathVariable("id") long id) {
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

}