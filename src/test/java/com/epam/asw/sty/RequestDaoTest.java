package com.epam.asw.sty;



import com.epam.asw.sty.dao.RequestDao;
import com.epam.asw.sty.dao.RequestDaoImpl;
import com.epam.asw.sty.model.Request;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

public class RequestDaoTest {

    private EmbeddedDatabase db;

    RequestDao requestDao;
    
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

    @After
    public void tearDown() {
        db.shutdown();
    }

}