package org.tourgune.pandora.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.pandora.bean.Rule;
 


public class RuleRowMapper implements RowMapper<Rule> {

	public Rule mapRow(ResultSet rs, int index) throws SQLException {

		Rule rule = new Rule(); 

		rule.setId(rs.getInt("id"));
		rule.setStatus_fence(rs.getInt("statusfence"));
		rule.setPeriod_fence(rs.getInt("periodfence"));
		rule.setFence_id(rs.getInt("fenceid"));
		rule.setDev_key(rs.getString("developerkey"));
		rule.setDesde(rs.getDate("desde"));
		rule.setHasta(rs.getDate("hasta"));
		rule.setDestination(rs.getInt("destinatario"));
		rule.setFromHour(rs.getInt("fromhour"));
		rule.setToHour(rs.getInt("tohour"));
		
		if(rs.getDate("desde")!=null){
			rule.setFromDate(rs.getDate("desde").toString());
		}
		if(rs.getDate("hasta")!=null){
			rule.setToDate(rs.getDate("hasta").toString());
		} 
		
		rule.setData(rs.getString("data"));
		
		return rule;
	}

}
