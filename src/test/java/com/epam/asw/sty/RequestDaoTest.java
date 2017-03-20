package com.epam.asw.sty;



import com.epam.asw.sty.dao.ChannelDao;
import com.epam.asw.sty.dao.ChannelDaoImpl;
import com.epam.asw.sty.dao.RequestDao;
import com.epam.asw.sty.dao.RequestDaoImpl;
import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Request;
import com.epam.asw.sty.service.SingleRSSFeedReader;
import com.epam.asw.sty.service.SingleRssFeedSavertoDB;
import com.sun.syndication.feed.synd.Converter;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.impl.ConverterForRSS20;
import com.sun.syndication.io.FeedException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RequestDaoTest {

    private EmbeddedDatabase db;

    RequestDao requestDao;

	@Mock(name="channelDaoImpl")
	ChannelDao channelDao;

	@InjectMocks
	SingleRssFeedSavertoDB singleRssFeedSavertoDB;

	@Mock
	Converter converter;

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
	public void testFindChannelbyUser() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		ChannelDaoImpl channelDao = new ChannelDaoImpl();
		channelDao.setNamedParameterJdbcTemplate(template);

		List<Channel> channel = channelDao.findByUser("Sergii");

		Assert.assertNotNull(channel);
		Assert.assertEquals(1, channel.get(0).getId());
		Assert.assertEquals("Sergii", channel.get(0).getUser());
		Assert.assertEquals("link: dou lenta", channel.get(0).getLink());

	}

	@Test
	public void testInsertNewRequest() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		RequestDaoImpl requestDao = new RequestDaoImpl();
		requestDao.setNamedParameterJdbcTemplate(template);

		Request requestTest = new Request();
		requestTest.setId(111);
		requestTest.setRequestor("TEST");
		requestTest.setStatus("");
		Object result = requestDao.insertNewEntry(requestTest);
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

/*		Channel channelTest = new Channel();
		channelTest.setId(111);
		channelTest.setUser("TEST");
		channelTest.setTitle("DOU");
		channelTest.setDescription("");
		channelTest.setLink("");
		channelTest.setLanguage("");
		Date testDate = new Date(2016, 03, 03, 01, 01, 01);
		channelTest.setPubDate(testDate);
		channelTest.setLastBuildDate(testDate);
		List<String> list = new ArrayList<>();
		list.add("");
		channelTest.setItems(list);

		Object result = channelDao.insertNewEntry(channelTest);*/
		List<Channel> channel = channelDao.findAll();

		Assert.assertNotNull(channel);
		Assert.assertEquals(1, channel.get(0).getId());
		Assert.assertEquals("DOU", channel.get(0).getTitle());
		Assert.assertEquals("Developers", channel.get(0).getDescription());

	}


	@Test
	@Ignore
	public void testInsertNewchannelFromWeb() throws IOException, FeedException {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		//ChannelDaoImpl channelDao = new ChannelDaoImpl();
		//channelDao.setNamedParameterJdbcTemplate(template);

		ConverterForRSS20 converter = new ConverterForRSS20();

		String url = "https://dou.ua/feed/";
		SingleRSSFeedReader singleRSSFeedReader = new SingleRSSFeedReader(url);
		SyndFeed rssFeed = singleRSSFeedReader.obtainRSSFeed(url);


		//SingleRssFeedSavertoDB singleRssFeedSavertoDB = new SingleRssFeedSavertoDB();
		Object result = singleRssFeedSavertoDB.saveRssFeedtoDB(rssFeed);
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