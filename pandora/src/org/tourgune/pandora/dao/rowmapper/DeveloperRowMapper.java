package org.tourgune.pandora.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.pandora.bean.Developer;
 


public class DeveloperRowMapper implements RowMapper<Developer> {

	public Developer mapRow(ResultSet rs, int index) throws SQLException {

		Developer developer = new Developer();

		developer.setId(rs.getInt("id"));
		developer.setKey(rs.getString("key"));  
		developer.setEmail(rs.getString("email"));  
		developer.setUsername(rs.getString("username"));  
		developer.setPassword(rs.getString("password"));
		developer.setActive(rs.getBoolean("active"));
		developer.setEndpoint(rs.getString("endpoint"));

		return developer;
	}

}
