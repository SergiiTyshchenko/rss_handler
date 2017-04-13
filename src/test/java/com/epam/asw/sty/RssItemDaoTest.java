package com.epam.asw.sty;


import com.epam.asw.sty.dao.ItemDao;
import com.epam.asw.sty.dao.ItemDaoImpl;
import com.epam.asw.sty.model.RssChannel;
import com.epam.asw.sty.model.RssItem;

import com.sun.syndication.feed.synd.Converter;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RssItemDaoTest {

	private EmbeddedDatabase db;



	@Mock(name="itemDaoImpl")
	ItemDao itemDao;

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
	public void testFindItemByExistingChannel() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		ItemDaoImpl itemDao = new ItemDaoImpl();
		itemDao.setNamedParameterJdbcTemplate(template);

		RssChannel rssChannel = new RssChannel();
		rssChannel.setShortid(0);
/*		List<RssItem> itemsTest = new ArrayList<RssItem>();
		RssItem itemTest = new RssItem();
		itemTest.setLink("https://dou.ua/feed/");
		itemTest.setChannelID(rssChannel.getShortid());
		itemsTest.add(itemTest);
		rssChannel.setItems(itemsTest);*/
		List<RssItem> rssItem = itemDao.findByChannelID(rssChannel.getShortid());

		Assert.assertNotNull(rssItem);
		Assert.assertEquals("dou lenta_Item", rssItem.get(0).getLink());

	}



	@Test
	public void testItemExist() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		ItemDaoImpl itemDao = new ItemDaoImpl();
		itemDao.setNamedParameterJdbcTemplate(template);

		List<RssItem> rssItem = itemDao.findAll();

		Assert.assertNotNull(rssItem);
		Assert.assertEquals("DOU_Item", rssItem.get(0).getTitle());
		Assert.assertEquals("dou lenta_Item", rssItem.get(0).getLink());

	}


	@After
	public void tearDown() {
		db.shutdown();
	}

}