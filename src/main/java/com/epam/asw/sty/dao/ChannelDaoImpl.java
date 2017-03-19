package com.epam.asw.sty.dao;

import com.epam.asw.sty.model.Channel;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChannelDaoImpl implements ChannelDao {


	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public List<Channel> findByTitle(String title) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", title);
        
		String sql = "SELECT * FROM channel WHERE title=:title";

		List<Channel> channels = namedParameterJdbcTemplate.query(
                    sql,
                    params,
                    new RequestMapper());
                    
        //new BeanPropertyRowMapper(Customer.class));
        
        return channels;
        
	}

	@Override
	public List<Channel> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT * FROM channel";

		List<Channel> channels = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
        
        return channels;
        
	}

	@Override
	public Object insertNewEntry(String title) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", 111);
			params.put("title", title);
			params.put("description", "TEST");
			params.put("link", "");
			params.put("language", "");
			params.put("dc_date", "");
			params.put("dc_language", "");
			params.put("item", "");
			String sql = "INSERT INTO CHANNEL " +
					"(ID, TITLE, DESCRIPTION, LINK, LANGUAGE, DC_DATE, DC_LANGUAGE, ITEM) VALUES (:id, :title, :description, :link," +
					" :language, :dc_date, :dc_language, :item)";

			Object result =  namedParameterJdbcTemplate.update(sql, params);
			return result;
		}

	@Override
	public Object insertNewSiteEntry(SyndFeed rssfeed) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", 333);
		params.put("title", rssfeed.getTitle());
		params.put("description", rssfeed.getDescription());
		params.put("link", rssfeed.getLink());
		params.put("language", rssfeed.getLanguage());
		params.put("dc_date", rssfeed.getPublishedDate());
		params.put("dc_language", rssfeed.getLanguage());
		params.put("item", rssfeed.getDescription());
		String sql = "INSERT INTO CHANNEL " +
				"(ID, TITLE, DESCRIPTION, LINK, LANGUAGE, DC_DATE, DC_LANGUAGE, ITEM) VALUES " +
				"(:id, :title, :description, :link, :language, :dc_date, :dc_language, :item)";

		Object result =  namedParameterJdbcTemplate.update(sql, params);
		return result;
	}

	private static final class RequestMapper implements RowMapper<Channel> {

		public Channel mapRow(ResultSet rs, int rowNum) throws SQLException {
			Channel channel = new Channel();
			channel.setId(rs.getInt("id"));
			channel.setTitle(rs.getString("title"));
			channel.setDescription(rs.getString("description"));
			channel.setLink(rs.getString("link"));
			channel.setLanguage(rs.getString("language"));
			channel.setPubDate(rs.getString("pubDate"));
			channel.setDcDate(rs.getString("dc_date"));
			channel.setDcLanguage(rs.getString("dc_language"));
			channel.setItem(rs.getString("item"));
			return channel;

		}
	}

}