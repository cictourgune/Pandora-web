package org.tourgune.pandora.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.pandora.bean.Rule;
 


public class RuleFenceRowMapper implements RowMapper<Rule> {

	public Rule mapRow(ResultSet rs, int index) throws SQLException {

		Rule rule = new Rule(); 

		rule.setId(rs.getInt("id"));
		rule.setStatus_fence(rs.getInt("statusfence"));
		rule.setPeriod_fence(rs.getInt("periodfence"));
		rule.setFence_id(rs.getInt("fenceid"));
		rule.setDev_key(rs.getString("developerkey"));
		rule.setFence_name(rs.getString("nombre"));
		rule.setData(rs.getString("data"));
		rule.setDestination(rs.getInt("destinatario"));
		rule.setFromHour(rs.getInt("fromhour"));
		rule.setToHour(rs.getInt("tohour"));

		return rule;
	}

}
