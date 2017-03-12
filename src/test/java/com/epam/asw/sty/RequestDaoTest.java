package com.epam.asw.sty;



import com.epam.asw.sty.dao.ChannelDao;
import com.epam.asw.sty.dao.ChannelDaoImpl;
import com.epam.asw.sty.dao.RequestDao;
import com.epam.asw.sty.dao.RequestDaoImpl;
import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Request;
import com.epam.asw.sty.service.SingleRSSFeedReader;
import com.epam.asw.sty.service.SingleRssFeedSavertoDB;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.io.IOException;
import java.util.List;


public class RequestDaoTest {

    private EmbeddedDatabase db;

    RequestDao requestDao;

	@Mock
	ChannelDao channelDao;

	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Before
    public void setUp() {
        //db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.addScript("sql/create-db.sql")
    		.addScript("sql/insert-data.sql")
    		.build();
    }

    @Test
    public void testFindByname() {
    	NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
    	RequestDaoImpl requestDao = new RequestDaoImpl();
		requestDao.setNamedParameterJdbcTemplate(template);

		List<Request> request = requestDao.findByName("Sergii");
  
    	Assert.assertNotNull(request);
    	Assert.assertEquals(1, request.get(0).getId());
    	Assert.assertEquals("Sergii", request.get(0).getRequestor());
    	Assert.assertEquals("Open", request.get(0).getStatus());

    }

	@Test
	public void testFindChannelbyTitle() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		ChannelDaoImpl channelDao = new ChannelDaoImpl();
		channelDao.setNamedParameterJdbcTemplate(template);

		List<Channel> channel = channelDao.findByTitle("title:DOU");

		Assert.assertNotNull(channel);
		Assert.assertEquals(1, channel.get(0).getId());
		Assert.assertEquals("title:DOU", channel.get(0).getTitle());
		Assert.assertEquals("link: dou lenta", channel.get(0).getLink());

	}

	@Test
	public void testInsertNewRequest() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		RequestDaoImpl requestDao = new RequestDaoImpl();
		requestDao.setNamedParameterJdbcTemplate(template);

		Object result = requestDao.insertNewEntry("TEST");
		List<Request> request = requestDao.findAll();

		Assert.assertNotNull(result);
		Assert.assertEquals(111, request.get(10).getId());
		Assert.assertEquals("TEST", request.get(10).getRequestor());
		Assert.assertEquals("", request.get(10).getStatus());

	}

	@Test
	public void testInsertNewchannel() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		ChannelDaoImpl channelDao = new ChannelDaoImpl();
		channelDao.setNamedParameterJdbcTemplate(template);

		Object result = channelDao.insertNewEntry("DOU");
		List<Channel> channel = channelDao.findAll();

		Assert.assertNotNull(result);
		Assert.assertEquals(111, channel.get(1).getId());
		Assert.assertEquals("DOU", channel.get(1).getTitle());
		Assert.assertEquals("TEST", channel.get(1).getDescription());

	}


	@Test
	public void testInsertNewchannelFromWeb() throws IOException, FeedException {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		ChannelDaoImpl channelDao = new ChannelDaoImpl();
		channelDao.setNamedParameterJdbcTemplate(template);

		String url = "https://dou.ua/feed/";
		SingleRSSFeedReader singleRSSFeedReader = new SingleRSSFeedReader(url);
		SyndFeed rssFeed = singleRSSFeedReader.obtainRSSFeed(url);


		SingleRssFeedSavertoDB singleRssFeedSavertoDB = new SingleRssFeedSavertoDB();
		Object result = singleRssFeedSavertoDB.saveRssFeedtoDB(rssFeed, channelDao);
		//Object result = saveRssFeedtoDB(rssFeed);
		//Object result = channelDao.insertNewEntry("DOU");
		List<Channel> channel = channelDao.findAll();

		Assert.assertNotNull(result);
		Assert.assertEquals(333, channel.get(1).getId());
		System.out.println(channel.get(1).getTitle());
	}
    @After
    public void tearDown() {
        db.shutdown();
    }

}