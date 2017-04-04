package com.epam.asw.sty;



import com.epam.asw.sty.dao.ChannelDao;
import com.epam.asw.sty.dao.ChannelDaoImpl;
import com.epam.asw.sty.model.Channel;
import com.sun.syndication.feed.synd.Converter;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ChannelDaoTest {

    private EmbeddedDatabase db;


	@Mock(name="channelDaoImpl")
	ChannelDao channelDao;


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


    @After
    public void tearDown() {
        db.shutdown();
    }

}