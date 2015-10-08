package org.tourgune.pandora.dao;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.tourgune.pandora.bean.Rule;
import org.tourgune.pandora.dao.rowmapper.RuleFenceRowMapper;
import org.tourgune.pandora.dao.rowmapper.RuleRowMapper;
 

@Service
public class RuleDao {
	
	public Boolean insertRule(Integer statusFence, Integer fenceId, String developerKey, Date desde, Date hasta, String data, Integer destinatario, Integer periodFence, Integer fromHour, Integer toHour){
		
		StringBuffer sql = new StringBuffer(); 
		Object[] parametros;
		int[] types;
 
		sql.append("INSERT INTO ");
		sql.append("reglas ");
		sql.append("(fenceid, statusfence, developerkey, desde, hasta, data, destinatario, periodfence, fromhour, tohour) ");
		sql.append("VALUES ");
		sql.append("(?,?,?,?,?,?,?,?,?,?)");

		parametros = new Object[] { fenceId, statusFence, developerKey, desde, hasta, data, destinatario, periodFence, fromHour, toHour};
		types = new int[] {Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.DATE, Types.DATE, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};

		return jdbcTemplate.update(sql.toString(), parametros, types)==1;   
	}
	
	public boolean updateRule(Integer fenceId, Integer statusFence, Date desde, Date hasta, String data, Integer destinatario, Integer periodFence, Integer fromHour, Integer toHour) {
		StringBuffer sql = new StringBuffer(); 
		Object[] parametros;
		int[] types;
		
		sql.append("UPDATE ");
		sql.append("reglas ");
		sql.append("SET statusfence=?, desde=?, hasta=?, data=?, destinatario=?, periodFence=?, fromhour=?, tohour=? ");
		sql.append("WHERE ");
		sql.append("fenceid=?");
		
		parametros = new Object[] { statusFence, desde, hasta, data, destinatario, periodFence, fromHour, toHour, fenceId };
		types = new int[] {Types.INTEGER, Types.DATE, Types.DATE, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};
		
		return jdbcTemplate.update(sql.toString(), parametros, types)==1;
	}
	
 
	public Rule getRuleByFence(Integer id){
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM ");
		sql.append("\"reglas\" WHERE fenceid=?"); 
		Rule rule = null;
		try {
			
			Object[] parametros;
			parametros = new Object[] { id };
			rule = (Rule) jdbcTemplate.queryForObject(sql.toString(),  parametros, new RuleRowMapper());

		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
		return rule;
	}
	
	public List getAllRules(){
		StringBuffer sql = new StringBuffer();
 
		sql.append("SELECT r.id, r.statusfence, r.periodfence, r.fenceid, r.developerkey, f.nombre, r.data, r.destinatario, r.fromhour, r.tohour FROM ");
		sql.append(" reglas AS r, fences AS f WHERE r.fenceid = f.id"); 
		
		List listaReglas = null;
		try {
			
			listaReglas =  jdbcTemplate.query(sql.toString(),  new RuleFenceRowMapper());

		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	
		return listaReglas;
	}

	 
	
	
	
	
 
	
	//-------------------------------------------------
	
	@Resource
	private JdbcTemplate jdbcTemplate;


 
}
