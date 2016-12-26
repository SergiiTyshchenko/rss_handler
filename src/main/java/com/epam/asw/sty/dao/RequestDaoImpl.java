package com.epam.asw.sty.dao;

import com.epam.asw.sty.model.Request;

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
public class RequestDaoImpl implements RequestDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public List<Request> findByName(String requestor) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("requestor", requestor);
        
		String sql = "SELECT * FROM requests WHERE requestor=:requestor";

		List<Request> requests = namedParameterJdbcTemplate.query(
                    sql,
                    params,
                    new RequestMapper());
                    
        //new BeanPropertyRowMapper(Customer.class));
        
        return requests;
        
	}

	@Override
	public List<Request> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT * FROM requests";
		
        List<Request> requests = namedParameterJdbcTemplate.query(sql, params, new RequestMapper());
        
        return requests;
        
	}

	private static final class RequestMapper implements RowMapper<Request> {

		public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
			Request request = new Request();
			request.setId(rs.getInt("id"));
			request.setRequestor(rs.getString("requestor"));
			request.setDescription(rs.getString("description"));
			request.setEmail(rs.getString("email"));
			request.setAssignee(rs.getString("assignee"));
			request.setStatus(rs.getString("status"));
			request.setPriority(rs.getInt("priority"));
			return request;
		}
	}

}