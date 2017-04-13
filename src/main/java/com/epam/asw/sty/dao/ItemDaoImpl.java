package com.epam.asw.sty.dao;


import com.epam.asw.sty.model.RssItem;
import com.sun.syndication.feed.rss.Description;
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
public class ItemDaoImpl implements ItemDao {


	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}


/*
	@Override
	public List<RssItem> findByChannel(RssChannel channel) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelID", channel.getShortid());

		String sql = "SELECT * FROM item WHERE CHANNELID=:channelID";

		List<RssItem> items = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());

		//new BeanPropertyRowMapper(Customer.class));

		return items;

	}
*/

	@Override
	public List<RssItem> findAll() {

		Map<String, Object> params = new HashMap<String, Object>();

		String sql = "SELECT * FROM item";

		List<RssItem> rssItems = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());

		return rssItems;

	}

	@Override
	public List<RssItem> findByChannelID(long shortid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shortid", shortid);


		String sql = "SELECT * FROM ITEM WHERE CHANNELID=:shortid";

		List<RssItem> rssItems = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());

		return rssItems;
	}

	@Override
	public Object insertNewEntry(RssItem rssItem) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", rssItem.getId());
		params.put("channelID", rssItem.getChannelID());
		params.put("title",  rssItem.getTitle());
		params.put("description", rssItem.getDescription().getValue());
		params.put("link", rssItem.getLink());
		params.put("pubDate", rssItem.getPubDate());
		String sql = "INSERT INTO ITEM " +
				"(ID, CHANNELID, TITLE, DESCRIPTION, LINK, PUBDATE) VALUES " +
				"(:id, :channelID, :title, :description, :link, :pubDate)";
				//"(2, 's', 's', 's', 's', 's', '2017-03-03', '2017-03-03', '')";

		Object result =  namedParameterJdbcTemplate.update(sql, params);
		return result;
	}


	@Override
	public Object removeEntryByChannelID(long  shortid) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelID", shortid);
		String sql = "DELETE FROM ITEM " +
				"WHERE CHANNELID=:channelID";

		Object result =  namedParameterJdbcTemplate.update(sql, params);
		return result;
	}


	@Override
	public List<RssItem> findLimitedItemsForOneChannelSortedByPubDate(String user, int count, long shortid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("count", count);
		params.put("shortid", shortid);

		String sql = "SELECT i.*,c.TITLE AS channelTitle FROM ITEM AS i JOIN CHANNEL AS c ON i.CHANNELID=c.SHORTID " +
				"WHERE c.USER=:user AND i.CHANNELID=:shortid " +
				"ORDER BY i.PUBDATE DESC " +
				" LIMIT :count";

		List<RssItem> rssItems = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		return rssItems;

	}


	@Override
	public List<RssItem> findAllItemsForOneChannelSortedByChannleID(String user, long shortid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("shortid", shortid);

		String sql = "SELECT i.*,c.TITLE AS channelTitle FROM ITEM AS i JOIN CHANNEL AS c ON i.CHANNELID=c.SHORTID " +
				"WHERE c.USER=:user " +
				"AND i.CHANNELID=:shortid " +
				"ORDER BY i.CHANNELID DESC";

		List<RssItem> rssItems = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		return rssItems;

	}



	@Override
	public List<RssItem> findLimitedItemsForAllChannelsSortedByPubDate(String user, int count){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("count", count);

		String sql = "SELECT i.*,c.TITLE AS channelTitle FROM ITEM AS i JOIN CHANNEL AS c ON i.CHANNELID=c.SHORTID " +
				"WHERE c.USER=:user " +
				"ORDER BY i.PUBDATE DESC " +
				"LIMIT :count";

		List<RssItem> rssItems = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		return rssItems;

	}



	@Override
	public List<RssItem> findAllItemsForAllChannelsSortedByChannelID(String user){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);

		String sql = "SELECT i.*,c.TITLE AS channelTitle FROM ITEM AS i JOIN CHANNEL AS c ON i.CHANNELID=c.SHORTID " +
				"WHERE c.USER=:user " +
				"ORDER BY i.CHANNELID DESC";

		List<RssItem> rssItems = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		return rssItems;

	}



	@Override
	public List<RssItem> findForUserbyChannelByCountSortedbyTitle(String user, int count, String orderItemField){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("count", count);
		params.put("orderItemField", orderItemField);

		String sql = "SELECT i.*,c.TITLE AS channelTitle FROM ITEM AS i JOIN CHANNEL AS c ON i.CHANNELID=c.SHORTID " +
				"WHERE c.USER=:user " +
				"ORDER BY i.CHANNELID ASC " +
				" LIMIT :count";

		List<RssItem> rssItems = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		return rssItems;

	}

	@Override
	public List<RssItem> findForUserByChannelID(long shortid, String user, int count){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("count", count);
		String additionalConditionWhere = "";
		if (shortid != -1) {
			params.put("shortid", shortid);
			additionalConditionWhere = "AND i.CHANNELID=:shortid ORDER BY i.CHANNELID ASC LIMIT :count";
		}

		String sql = "SELECT i.*,c.TITLE AS channelTitle FROM ITEM AS i JOIN CHANNEL AS c ON i.CHANNELID=c.SHORTID " +
				"WHERE c.USER=:user " + additionalConditionWhere;

		List<RssItem> rssItems = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		return rssItems;
	}

	private static final class RequestMapper implements RowMapper<RssItem> {



		public RssItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			RssItem rssItem = new RssItem();
			rssItem.setId(rs.getString("id"));
			rssItem.setChannelID(rs.getLong("channelID"));
			rssItem.setTitle(rs.getString("title"));
			Description itemDescription = new Description();
			itemDescription.setValue(rs.getString("description"));;
			rssItem.setDescription(itemDescription);
			rssItem.setLink(rs.getString("link"));
			rssItem.setPubDate(rs.getTimestamp("pubDate"));
			final int itemTableColumnCount = 6;
			if(rs.getMetaData().getColumnCount() != 6) {
				rssItem.setChannelTitle(rs.getString("channelTitle"));
			}
			return rssItem;

		}
	}

}