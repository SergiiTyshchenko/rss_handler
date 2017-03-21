package com.epam.asw.sty.dao;



import com.epam.asw.sty.model.Channel;
import com.sun.syndication.feed.synd.SyndFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ChannelDaoImpl implements ChannelDao {


	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}


	@Override
	public List<Channel> findByUser(String user) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);

		String sql = "SELECT * FROM channel WHERE USER=:user";

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
	public Object insertNewEntry(Channel channel) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",channel.getId());
		params.put("user", channel.getUser());
		params.put("title",  channel.getTitle());
		params.put("description", channel.getDescription());
		params.put("link", channel.getLink());
		params.put("language", channel.getLanguage());
		params.put("pubDate", channel.getPubDate());
		params.put("lastBuildDate", channel.getPubDate());
		params.put("items", channel.getId());
		String sql = "INSERT INTO CHANNEL " +
				"(ID, USER, TITLE, DESCRIPTION, LINK, LANGUAGE, PUBDATE, LASTBUILDDATE, ITEMS) VALUES " +
				"(:id, :user, :title, :description, :link, :language, :pubDate, :lastBuildDate, :items)";
				//"(2, 's', 's', 's', 's', 's', '2017-03-03', '2017-03-03', '')";

		Object result =  namedParameterJdbcTemplate.update(sql, params);

		return result;
	}

	@Override
	public Object removeEntryByID(long id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String sql = "DELETE FROM CHANNEL " +
				"WHERE ID=:id";

		Object result =  namedParameterJdbcTemplate.update(sql, params);
		return result;
	}

	@Override
	public Object insertNewSiteEntry(SyndFeed rssfeed) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", 333);
		params.put("user", "RSS");
		params.put("title", rssfeed.getTitle());
		params.put("description", rssfeed.getDescription());
		params.put("link", rssfeed.getLink());
		params.put("language", rssfeed.getLanguage());
		params.put("pubDate", rssfeed.getPublishedDate());
		params.put("lastBuildDate", rssfeed.getPublishedDate());
		params.put("items", 3333);
		String sql = "INSERT INTO CHANNEL " +
				"(ID, USER, TITLE, DESCRIPTION, LINK, LANGUAGE, PUBDATE, LASTBUILDDATE, ITEMS) VALUES (:id, :user, :title," +
				" :description, :link, :language, :pubDate, :lastBuildDate, :items)";

		Object result =  namedParameterJdbcTemplate.update(sql, params);
		return result;
	}

	@Override
	public Object updateEntry(Channel channel) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",channel.getId());
		params.put("user", channel.getUser());
		params.put("title",  channel.getTitle());
		params.put("description", channel.getDescription());
		params.put("link", channel.getLink());
		params.put("language", channel.getLanguage());
		params.put("pubDate", channel.getPubDate());
		params.put("lastBuildDate", channel.getLastBuildDate());
		params.put("items", channel.getId());
		String sql = "UPDATE CHANNEL " +
				"SET (USER, TITLE, DESCRIPTION, LINK, LANGUAGE, PUBDATE, LASTBUILDDATE, ITEMS) = " +
				"(:user, :title, :description, :link, :language, :pubDate, :lastBuildDate, :items) " +
				"WHERE ID=:id";

		Object result =  namedParameterJdbcTemplate.update(sql, params);
		return result;
	}


	private static final class RequestMapper implements RowMapper<Channel> {

		public Channel mapRow(ResultSet rs, int rowNum) throws SQLException {
			Channel channel = new Channel();
			channel.setId(rs.getInt("id"));
			channel.setUser(rs.getString("user"));
			channel.setTitle(rs.getString("title"));
			channel.setDescription(rs.getString("description"));
			channel.setLink(rs.getString("link"));
			channel.setLanguage(rs.getString("language"));
			channel.setPubDate(rs.getTimestamp("pubDate"));
			channel.setLastBuildDate(rs.getTimestamp("lastBuildDate"));
			Array rsItemsArray =  rs.getArray("items");
			Object[] rsItemsStaringArray = (Object[])rsItemsArray.getArray();
			List<String> list = new ArrayList<>();
			for (Object i : rsItemsStaringArray) {
				list.add(i.toString());
			}
			channel.setItems(list);
			return channel;

		}
	}

}