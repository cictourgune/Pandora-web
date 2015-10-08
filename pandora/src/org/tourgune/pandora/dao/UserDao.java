package org.tourgune.pandora.dao;

import java.sql.Types;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service 
public class UserDao {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	
	public Integer insertUser(String userId, Integer devId){
		
		StringBuffer sql = new StringBuffer();
		 
		sql.append("INSERT INTO users ");
		sql.append("(user_id, dev_id) ");
		sql.append("VALUES ");
		sql.append("(?,?)");
		 
		Object[] parametros;
		int[] types;

		parametros = new Object[] { userId, devId };
		
		types = new int[] { Types.VARCHAR, Types.INTEGER};
		
		if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
			return 1;
		}else{
			return -1;
		} 	
		
	}
	
	
	public Boolean deleteUser(String userId){
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM ");
		sql.append("\"users\" ");
		sql.append("WHERE user_id = ?");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { userId };
		types = new int[] { Types.VARCHAR };

		return jdbcTemplate.update(sql.toString(), parametros, types)==1; 
	}
	
	public Boolean deleteUserRule(Integer ruleId){
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM ");
		sql.append("\"users_rules\" ");
		sql.append("WHERE rule_id = ?");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { ruleId };
		types = new int[] { Types.INTEGER };

		return jdbcTemplate.update(sql.toString(), parametros, types)==1; 
	}
	
	public Integer insertUserRule(Integer userId, Integer ruleId){
		
		try{
			
			StringBuffer sql = new StringBuffer();
			 
			sql.append("INSERT INTO users_rules ");
			sql.append("(user_id, rule_id) ");
			sql.append("VALUES ");
			sql.append("(?, ?)");
			 
			Object[] parametros;
			int[] types;

			parametros = new Object[] { userId, ruleId };
			
			types = new int[] { Types.INTEGER, Types.INTEGER};
			
			if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
				return 1;
			}else{
				return -1;
			} 	
			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		} 
	}
	
	public Boolean existsUserRule(Integer userId, Integer ruleId){
		
		try {
			StringBuffer sql = new StringBuffer(); 
			
			sql.append("SELECT count(*) FROM ");
			sql.append("users_rules ");
			sql.append("WHERE user_id = ? AND rule_id=?"); 
	
			Object[] parametros;
			int[] types;

			parametros = new Object[] { userId, ruleId }; 
			types = new int[] { Types.INTEGER, Types.INTEGER};
	 
			Integer num = jdbcTemplate.queryForObject(sql.toString(), parametros, types, Integer.class); 			 
			
			if(num==0){
				return false;
			}  
			return true; 
			
        } catch (Exception e) { 
            return false;
        }
		 
	}
	
	public Integer getUserDBId(Integer devId, String userId){
		
		try {
			StringBuffer sql = new StringBuffer();
	 
			sql.append("SELECT id ");
			sql.append("FROM users ");
			sql.append("WHERE user_id = ? AND dev_id=? "); 
	
			Object[] parametros;
			parametros = new Object[] { userId, devId };
	 
			Integer id = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
			 
			return id; 
			
        } catch (EmptyResultDataAccessException e) { 
            return -1;
        }
		
	}
	
	public Boolean existsUser(String userId, Integer developerId){
		
		try {
			StringBuffer sql = new StringBuffer(); 
			
			sql.append("SELECT count(*) FROM ");
			sql.append("users  ");
			sql.append("WHERE user_id = ? AND dev_id=?"); 
	
			Object[] parametros;
			int[] types;

			parametros = new Object[] { userId , developerId }; 
			types = new int[] { Types.VARCHAR , Types.INTEGER};
	 
			Integer num = jdbcTemplate.queryForObject(sql.toString(), parametros, types, Integer.class); 			 
			
			if(num==0){
				return false;
			}  
			return true; 
			
        } catch (Exception e) { 
            return false;
        }
		 
	}
	
	
 
	 
}
