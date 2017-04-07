package com.epam.asw.sty.dao;


import com.epam.asw.sty.model.Channel;
import com.epam.asw.sty.model.Item;
import com.sun.syndication.feed.rss.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public List<Item> findByChannel(Channel channel) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelID", channel.getShortid());

		String sql = "SELECT * FROM item WHERE CHANNELID=:channelID";

		List<Item> items = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());

		//new BeanPropertyRowMapper(Customer.class));

		return items;

	}
*/

	@Override
	public List<Item> findAll() {

		Map<String, Object> params = new HashMap<String, Object>();

		String sql = "SELECT * FROM item";

		List<Item> items = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());

		return items;

	}

	@Override
	public List<Item> findByChannelID(long shortid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shortid", shortid);


		String sql = "SELECT * FROM ITEM WHERE CHANNELID=:shortid";

		List<Item> items = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());

		return items;
	}

	@Override
	public Object insertNewEntry(Item item) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",item.getId());
		params.put("channelID", item.getChannelID());
		params.put("title",  item.getTitle());
		params.put("description", item.getDescription().getValue());
		params.put("link", item.getLink());
		params.put("pubDate", item.getPubDate());
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
	public List<Item> findForUserByCountSortedByDate(String user, int count, String orderItemField, long shortid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("count", count);
		String additionalConditionWhere = "";
		if (shortid != -1) {
			params.put("shortid", shortid);
			additionalConditionWhere = "AND i.CHANNELID=:shortid ";
		}

		String additionalConditionOrder = "ORDER BY i." + orderItemField.toUpperCase() + " DESC";


		String sql = "SELECT i.*,c.TITLE AS channelTitle FROM ITEM AS i JOIN CHANNEL AS c ON i.CHANNELID=c.SHORTID " +
				"WHERE c.USER=:user " + additionalConditionWhere + additionalConditionOrder +
				" LIMIT :count";

		List<Item> items = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		return items;

	}

	@Override
	public List<Item> findForUserbyChannelByCountSortedbyTitle(String user, int count, String orderItemField){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("count", count);
		params.put("orderItemField", orderItemField);

		String sql = "SELECT i.*,c.TITLE AS channelTitle FROM ITEM AS i JOIN CHANNEL AS c ON i.CHANNELID=c.SHORTID " +
				"WHERE c.USER=:user " +
				"ORDER BY i.CHANNELID ASC " +
				" LIMIT :count";

		List<Item> items = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		return items;

	}

	@Override
	public List<Item> findForUserByChannelID(long shortid, String user,  int count){
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

		List<Item> items = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
		return items;
	}

	private static final class RequestMapper implements RowMapper<Item> {



		public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
			Item item = new Item();
			item.setId(rs.getString("id"));
			item.setChannelID(rs.getLong("channelID"));
			item.setTitle(rs.getString("title"));
			Description itemDescription = new Description();
			itemDescription.setValue(rs.getString("description"));;
			item.setDescription(itemDescription);
			item.setLink(rs.getString("link"));
			item.setPubDate(rs.getTimestamp("pubDate"));
			final int itemTableColumnCount = 6;
			if(rs.getMetaData().getColumnCount() != 6) {
				item.setChannelTitle(rs.getString("channelTitle"));
			}
			return item;

		}
	}

}