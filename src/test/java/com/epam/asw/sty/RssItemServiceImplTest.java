package com.epam.asw.sty;

import com.epam.asw.sty.dao.ItemDao;
import com.epam.asw.sty.dao.ItemDaoImpl;
import com.epam.asw.sty.model.RssItem;
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

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RunWith(MockitoJUnitRunner.class)
public class RssItemServiceImplTest {


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
    public void saveItemTest() {

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
        ItemDaoImpl itemDao = new ItemDaoImpl();
        itemDao.setNamedParameterJdbcTemplate(template);

        RssItem rssItemTest = new RssItem();
        rssItemTest.setLink("https://dou.ua/feed/");
        rssItemTest.setChannelID(0);
        rssItemTest.setTitle("TEST_ITEM_TITLE");
        Description description = new Description();
        description.setValue("TEST_ITEM_DESCRIPTION");
        rssItemTest.setDescription(description);
        Calendar calendar = Calendar.getInstance();
        Date testDate =  calendar.getTime();
        rssItemTest.setPubDate(testDate);

        Object result = itemDao.insertNewEntry(rssItemTest);
        List<RssItem> rssItems = itemDao.findAll();
        Assert.assertNotNull(result);
        Assert.assertEquals("TEST_ITEM_DESCRIPTION", rssItems.get(2).getDescription().getValue());

    }

}