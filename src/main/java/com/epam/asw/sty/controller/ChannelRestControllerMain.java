package com.epam.asw.sty.controller;


import com.epam.asw.sty.dao.ChannelDao;
//import com.epam.asw.sty.service.DBChannelStats;
import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.service.ChannelService;
import com.epam.asw.sty.service.SingleRSSFeedReader;
import com.epam.asw.sty.service.SingleRssFeedSavertoDB;

import com.sun.syndication.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class ChannelRestControllerMain {



	@Resource(name="channelDaoImpl")
	private ChannelDao channelDao;

	@Resource(name="channelServiceImpl")
	private ChannelService channelService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@RequestMapping(value = "/db/", method = RequestMethod.GET)
	public String welcome(Model model) {

		String logDebugMessage = "epam_sergii";

		logger.debug("DEBUG message {}.", logDebugMessage);

		List<Channel> channel = channelDao.findByUser("Sergii");

		List<Channel> channels = channelDao.findAll();

		logger.info("INFO message {}.", channels);
		model.addAttribute("channel", channels);

		return "welcome";

	}

	//-------------------Retrieve Channels Statistic Data--------------------------------------------------------

/*	@RequestMapping(value = "/db/channel/stats", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> DBChannelsStats() {
		List<Channel> channels = channelService.populateChannelsFromDB();
		Integer channels_count;
		if(channels.isEmpty()){
			return new ResponseEntity<Map<String,Object>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		else {
			DBChannelStats statsTest = new DBChannelStats();
			Map<String,Object> stats = new java.util.HashMap<>();
			channels_count = statsTest.DBChannelCount(channels);
			stats.put("DB Channel count: ", channels_count);
			List<String> channel_count_per_user = new ArrayList<String>();
			channel_count_per_user=statsTest.ChannelsPerUser(channels);
			stats.put("DB Channel count per user: ",channel_count_per_user);
			return new ResponseEntity<Map<String,Object>>(stats, HttpStatus.OK);
		}
	}*/




	//-------------------Retrieve All RSS feeds--------------------------------------------------------

	@RequestMapping(value = "/db/rss/", method = RequestMethod.GET)
	public ResponseEntity<List<Channel>> listAllDBRssFeeds() {

		List<Channel> channels = channelDao.findAll();
		if(channels.isEmpty()){
			return new ResponseEntity<List<Channel>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Channel>>(channels, HttpStatus.OK);
	}

	//-------------------Add New RSS Feed--------------------------------------------------------


	@RequestMapping(value = "/db/rss/insert", method = RequestMethod.GET)
	public ResponseEntity<Object> DBchannelInsert() throws IOException, FeedException {
		String url = "https://dou.ua/feed/";
		SingleRSSFeedReader singleRSSFeedReader = new SingleRSSFeedReader(url);
		SingleRssFeedSavertoDB singleRssFeedSavertoDB = new SingleRssFeedSavertoDB();
		Object result = singleRssFeedSavertoDB.saveRssFeedtoDB(singleRSSFeedReader.obtainRSSFeed(url), channelDao);
		//Object result = null;
		if(result==null){
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

}
