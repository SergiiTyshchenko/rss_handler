package com.epam.asw.sty;

import com.epam.asw.sty.controller.ItemsRestController;
import com.epam.asw.sty.dao.ItemDao;
import com.epam.asw.sty.dao.ItemDaoImpl;
import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;
import com.sun.syndication.feed.rss.Description;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import sun.security.krb5.internal.crypto.Des;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceImplTest {


    private EmbeddedDatabase db;

    private static final AtomicLong counter = new AtomicLong();

    @Mock(name="itemDaoImpl")
    ItemDao itemDao;

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
    public void saveItemTest() throws Exception {

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
        ItemDaoImpl itemDao = new ItemDaoImpl();
        itemDao.setNamedParameterJdbcTemplate(template);

        Item itemTest = new Item();
        itemTest.setLink("https://dou.ua/feed/");
        itemTest.setChannelID("111");
        itemTest.setTitle("TEST_ITEM_TITLE");
        Description description = new Description();
        description.setValue("TEST_ITEM_DESCRIPTION");
        itemTest.setDescription(description);
        Date testDate = new Date(2016, 03, 03, 01, 01, 01);
        itemTest.setPubDate(testDate);

        Object result = itemDao.insertNewEntry(itemTest);
        List<Item> items = itemDao.findAll();
        Assert.assertNotNull(result);
        Assert.assertEquals("TEST_ITEM_DESCRIPTION", items.get(1).getDescription().getValue());

    }

}