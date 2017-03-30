package com.epam.asw.sty;



import com.epam.asw.sty.dao.ChannelDao;
import com.epam.asw.sty.dao.ChannelDaoImpl;
import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.service.rss.RSSFeedReader;
import com.epam.asw.sty.service.rss.RSSfeedSavertoDB;
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

import java.io.IOException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ChannelDaoTest {

    private EmbeddedDatabase db;


	@Mock(name="channelDaoImpl")
	ChannelDao channelDao;

	@InjectMocks
	RSSfeedSavertoDB RSSfeedSavertoDB;

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
	public void testFindChannelbyUser() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		ChannelDaoImpl channelDao = new ChannelDaoImpl();
		channelDao.setNamedParameterJdbcTemplate(template);
		String userTest = "user";
		List<Channel> channel = channelDao.findByUser(userTest);

		Assert.assertNotNull(channel);
		Assert.assertEquals(userTest, channel.get(0).getUser());
		Assert.assertEquals("https://dou.ua/feed/", channel.get(0).getLink());

	}



	@Test
	public void testChannelExist() {
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
		//Assert.assertEquals(1, channel.get(0).getId());
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
		RSSFeedReader RSSFeedReader = new RSSFeedReader(url);
		SyndFeed rssFeed = RSSFeedReader.obtainRSSFeed(url);


		//RSSFeedSavertoDB RSSFeedSavertoDB = new RSSFeedSavertoDB();
		Object result = RSSfeedSavertoDB.saveRssFeedtoDB(rssFeed);
		//Object result = saveRssFeedtoDB(rssFeed);
		//Object result = channelDao.insertNewEntry("DOU");
		List<Channel> channel = channelDao.findAll();

		Assert.assertNotNull(result);
		//Assert.assertEquals(333, channel.get(1).getId());
		System.out.println(channel.get(1).getTitle());
	}
    @After
    public void tearDown() {
        db.shutdown();
    }

}