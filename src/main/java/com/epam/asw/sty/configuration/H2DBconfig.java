package com.epam.asw.sty.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;

import org.h2.tools.Server;

@Configuration
public class H2DBconfig {

    private final static Logger logger = LoggerFactory.getLogger(H2DBconfig.class);

    private static final String DBNAME = "mytest";

    // jdbc:h2:mem:testdb
    @Bean
    public DataSource dataSource() {


        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).addScript("sql/create-db.sql").addScript("sql/insert-data.sql").build();
        return db;

    }

    // Start WebServer, access http://localhost:8082
    @Bean(initMethod = "start", destroyMethod = "stop")
    public static Server startDBManager() throws SQLException, ClassNotFoundException, IOException {

        Server webServer = Server.createWebServer("-webAllowOthers","-webPort","8088"); // (4a)
        //Server server = Server.createTcpServer("-tcpAllowOthers","-tcpPort","9088");
        logger.debug("H2 DB Web Server URL: jdbc:h2:" + webServer.getURL() + "/mem:" + DBNAME);
        return Server.createPgServer();
    }
}
