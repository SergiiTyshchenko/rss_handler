package com.epam.asw.sty;


import com.epam.asw.sty.dao.ItemDao;
import com.epam.asw.sty.dao.ItemDaoImpl;
import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;

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
public class ItemDaoTest {

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

		Channel channel = new Channel();
		channel.setShortid(0);
/*		List<Item> itemsTest = new ArrayList<Item>();
		Item itemTest = new Item();
		itemTest.setLink("https://dou.ua/feed/");
		itemTest.setChannelID(channel.getShortid());
		itemsTest.add(itemTest);
		channel.setItems(itemsTest);*/
		List<Item> item = itemDao.findByChannelID(channel.getShortid());

		Assert.assertNotNull(item);
		Assert.assertEquals("dou lenta_Item", item.get(0).getLink());

	}



	@Test
	public void testItemExist() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
		ItemDaoImpl itemDao = new ItemDaoImpl();
		itemDao.setNamedParameterJdbcTemplate(template);

		List<Item> item = itemDao.findAll();

		Assert.assertNotNull(item);
		Assert.assertEquals("DOU_Item", item.get(0).getTitle());
		Assert.assertEquals("dou lenta_Item", item.get(0).getLink());

	}


	@After
	public void tearDown() {
		db.shutdown();
	}

}