package com.epam.asw.sty.dao;



import com.epam.asw.sty.model.RssChannel;
import com.sun.syndication.feed.synd.SyndFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ChannelDaoImpl implements ChannelDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}


	@Override
	public List<RssChannel> findByUser(String user) {

		Map<String, Object> params = new HashMap<>();
		params.put("user", user);

		String sql = "SELECT * FROM channel WHERE USER=:user";

		List<RssChannel> rssChannels = namedParameterJdbcTemplate.query(
				sql,
				params,
				new RequestMapper());

		return rssChannels;

	}

	@Override
	public RssChannel findByLink(String link) {

		Map<String, Object> params = new HashMap<>();
		params.put("link", link);

		String sql = "SELECT * FROM channel WHERE LINK=:link";

		List<RssChannel> rssChannels = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		if(rssChannels.isEmpty() ) {
			return null;
		} else {
			return rssChannels.get(0);
		}

	}

	@Override
	public RssChannel findByShortID(long shortid) {

		Map<String, Object> params = new HashMap<>();
		params.put("shortid", shortid);

		String sql = "SELECT * FROM channel WHERE SHORTID=:shortid";

		List<RssChannel> rssChannels = namedParameterJdbcTemplate.query(
				sql,
				params,
				new RequestMapper());

		//new BeanPropertyRowMapper(Customer.class));

		if(rssChannels.isEmpty() ) {
			return null;
		} else {
			return rssChannels.get(0);
		}

	}

	@Override
	public RssChannel findLastAddedChannel() {



		String sql = "SELECT * FROM channel ORDER BY SHORTID DESC LIMIT 1";

		List<RssChannel> rssChannels = namedParameterJdbcTemplate.query(
				sql,
				new RequestMapper());

		if(rssChannels.isEmpty() ) {
			return null;
		} else {
			return rssChannels.get(0);
		}

	}

	@Override
	public RssChannel findByID(String id) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		String sql = "SELECT * FROM channel WHERE ID=:id";

		List<RssChannel> rssChannels = namedParameterJdbcTemplate.query(
				sql,
				params,
				new RequestMapper());

		if(rssChannels.isEmpty() ) {
			return null;
		} else {
			return rssChannels.get(0);
		}

	}

	@Override
	public List<RssChannel> findAll() {

		Map<String, Object> params = new HashMap<>();

		String sql = "SELECT * FROM channel ORDER BY SHORTID";

		List<RssChannel> rssChannels = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());

		return rssChannels;

	}

	@Override
	public Object insertNewEntry(RssChannel rssChannel) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", rssChannel.getId());
		params.put("shortid", rssChannel.getShortid());
		params.put("user", rssChannel.getUser());
		params.put("title",  rssChannel.getTitle());
		params.put("description", rssChannel.getDescription());
		params.put("link", rssChannel.getLink());
		params.put("language", rssChannel.getLanguage());
		params.put("pubDate", rssChannel.getPubDate());
		params.put("lastBuildDate", rssChannel.getPubDate());
		params.put("items", rssChannel.getItemsCount());
		String sql = "INSERT INTO CHANNEL " +
				"(ID, SHORTID, USER, TITLE, DESCRIPTION, LINK, LANGUAGE, PUBDATE, LASTBUILDDATE, ITEMS) VALUES " +
				"(:id, :shortid, :user, :title, :description, :link, :language, :pubDate, :lastBuildDate, :items)";
		Object result =  namedParameterJdbcTemplate.update(sql, params);

		return result;
	}

	@Override
	public Object removeEntryByID(String  id) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		String sql = "DELETE FROM CHANNEL " +
				"WHERE ID=:id";

		Object result =  namedParameterJdbcTemplate.update(sql, params);
		return result;
	}

	@Override
	public Object insertNewSiteEntry(SyndFeed rssfeed) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", 333);
		params.put("shortid",0);
		params.put("user", "RSS");
		params.put("title", rssfeed.getTitle());
		params.put("description", rssfeed.getDescription());
		params.put("link", rssfeed.getLink());
		params.put("language", rssfeed.getLanguage());
		params.put("pubDate", rssfeed.getPublishedDate());
		params.put("lastBuildDate", rssfeed.getPublishedDate());
		params.put("items", 3);
		String sql = "INSERT INTO CHANNEL " +
				"(ID, ,SHORTID, USER, TITLE, DESCRIPTION, LINK, LANGUAGE, PUBDATE, LASTBUILDDATE, ITEMS) " +
				"VALUES (:id, :shortid, :user, :title, :description, :link, :language, :pubDate, :lastBuildDate, :items)";

		Object result =  namedParameterJdbcTemplate.update(sql, params);
		return result;
	}

	@Override
	public Object updateEntry(RssChannel rssChannel) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", rssChannel.getId());
		params.put("shortid", rssChannel.getShortid());
		params.put("user", rssChannel.getUser());
		params.put("title",  rssChannel.getTitle());
		params.put("description", rssChannel.getDescription());
		params.put("link", rssChannel.getLink());
		params.put("language", rssChannel.getLanguage());
		params.put("pubDate", rssChannel.getPubDate());
		params.put("lastBuildDate", rssChannel.getLastBuildDate());
		params.put("items", rssChannel.getItemsCount());
		String sql = "UPDATE CHANNEL " +
				"SET (SHORTID, USER, TITLE, DESCRIPTION, LINK, LANGUAGE, PUBDATE, LASTBUILDDATE, ITEMS) = " +
				"(:shortid, :user, :title, :description, :link, :language, :pubDate, :lastBuildDate, :items) " +
				"WHERE ID=:id";

		Object result =  namedParameterJdbcTemplate.update(sql, params);
		return result;
	}


	private static final class RequestMapper implements RowMapper<RssChannel> {

		public RssChannel mapRow(ResultSet rs, int rowNum) throws SQLException {
			RssChannel rssChannel = new RssChannel();
			rssChannel.setId(rs.getString("id"));
			rssChannel.setShortid(rs.getInt("shortid"));
			rssChannel.setUser(rs.getString("user"));
			rssChannel.setTitle(rs.getString("title"));
			rssChannel.setDescription(rs.getString("description"));
			rssChannel.setLink(rs.getString("link"));
			rssChannel.setLanguage(rs.getString("language"));
			rssChannel.setPubDate(rs.getTimestamp("pubDate"));
			rssChannel.setLastBuildDate(rs.getTimestamp("lastBuildDate"));
			Array rsItemsArray =  rs.getArray("items");
			Object[] rsItemsStaringArray = (Object[])rsItemsArray.getArray();
			List<String> list = new ArrayList<>();
			for (Object i : rsItemsStaringArray) {
				list.add(i.toString());
			}
			rssChannel.setItems(list);
			rssChannel.setItemsCount(rs.getInt("items"));
			return rssChannel;

		}
	}

}