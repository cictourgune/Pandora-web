package org.tourgune.pandora.dao;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Types;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tourgune.pandora.bean.Developer;
import org.tourgune.pandora.dao.rowmapper.DeveloperRowMapper;
import org.tourgune.pandora.util.Configuration;

@Service 
public class DeveloperDao {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	
	public String createDeveloper(Developer developer) { 
		
		StringBuffer sql = new StringBuffer();
	
		String key = UUID.randomUUID().toString();
		
		//encriptar password
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword(developer.getPassword(), null);

		sql.append("INSERT INTO developers ");
		sql.append("(username, password, email, key) ");
		sql.append("VALUES ");
		sql.append("(?, ?, ?, ?)");
		 
		Object[] parametros;
		int[] types;

		parametros = new Object[] { developer.getUsername(), hashedPass, developer.getEmail(), key };
		
		types = new int[] { Types.VARCHAR, Types.VARCHAR , Types.VARCHAR, Types.VARCHAR };
		
		if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
			return key;
		}else{
			return "-1";
		} 	
	}
	
	public boolean existsDeveloperKey(String key){
		
		try {
			StringBuffer sql = new StringBuffer(); 
			
			sql.append("SELECT count(*) FROM ");
			sql.append("developers ");
			sql.append("WHERE key = ? "); 
	
			Object[] parametros;
			parametros = new Object[] { key.trim() };
	 
			Integer keyNum = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class); 			 
			
			if(keyNum==0){
				return false;
			} 
			
			return true; 
			
        } catch (Exception e) { 
            return false;
        }
	}
	
	
	public String getDeveloperPassword(String username){
		
		try {
			StringBuffer sql = new StringBuffer();
	 
			sql.append("SELECT password ");
			sql.append("FROM developers ");
			sql.append("WHERE username = ? AND active ='true'"); 
	
			Object[] parametros;
			parametros = new Object[] { username };
	 
			String pass = jdbcTemplate.queryForObject(sql.toString(), parametros, String.class);
			 
			return pass; 
			
        } catch (EmptyResultDataAccessException e) { 
            return null;
        }
	}
	
	 
	
	public Integer getDeveloperIdByKey (String key){
		
		try {
			StringBuffer sql = new StringBuffer();
	 
			sql.append("SELECT id ");
			sql.append("FROM developers ");
			sql.append("WHERE key = ? "); 
	
			Object[] parametros;
			parametros = new Object[] { key };
	 
			Integer id = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
			 
			return id; 
			
        } catch (EmptyResultDataAccessException e) { 
            return -1;
        }
	}
	
	public Developer getDeveloperByKey (String key){
		
		try {
			StringBuffer sql = new StringBuffer();
	 
			sql.append("SELECT * ");
			sql.append("FROM developers ");
			sql.append("WHERE key = ? "); 
	
			Object[] parametros;
			parametros = new Object[] { key };
	 
			Developer developer = (Developer) jdbcTemplate.queryForObject(sql.toString(), parametros, new DeveloperRowMapper());
			 
			return  developer; 
			
        } catch (EmptyResultDataAccessException e) { 
            return null;
        }
	}
 
	
	public Integer existsUsername(String username, String email){
		
		try {
			StringBuffer sql = new StringBuffer(); 
			
			sql.append("SELECT count(*) FROM ");
			sql.append(" developers ");
			sql.append("WHERE username = ? ");
			sql.append("OR email = ?");
	
			Object[] parametros;
			parametros = new Object[] { username, email };
	 
			Integer num = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class); 			 
			
			if(num==0){
				return -1;
			}  
			return 1; 
			
        } catch (Exception e) { 
            return -1;
        }
	}
	
	 	
	public Integer activateDeveloper(String key) { 
		
		StringBuffer sql = new StringBuffer(); 

		sql.append("UPDATE developers ");
		sql.append("SET ");
		sql.append("active = 'true' ");
		sql.append("WHERE key = ?");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { key };
		
		types = new int[] { Types.VARCHAR };
		
		if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
			return 1;
		}else{
			return -1;
		} 	
	}
	
	public Developer getDeveloperByUsername(String username){
		
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM ");
		sql.append("\"developers\" WHERE username=?"); 
		Developer developer = null;
		try {
			
			Object[] parametros;
			parametros = new Object[] { username };
			developer = (Developer) jdbcTemplate.queryForObject(sql.toString(),  parametros, new DeveloperRowMapper());

		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
		return developer;
		
		
		
	}

	public Integer updateDeveloper(Developer developer) {		
		StringBuffer sql = new StringBuffer(); 
		Object[] parametros=new Object[0];
		int[] types=new int[0];
		
		sql.append("UPDATE developers ");
		sql.append("SET ");
		
		
		if(developer.getEndpoint() != null){
			sql.append("endpoint = ?");
			
			parametros = addToObjectArray(parametros, developer.getEndpoint());
			types = addToIntArray(types, Types.VARCHAR);
		}

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		sql.append("WHERE username = ?");
		
		parametros = addToObjectArray(parametros, username);
		types = addToIntArray(types, Types.VARCHAR);

//		parametros = new Object[] { developer.getCallback_url(), username };		
//		types = new int[] { Types.VARCHAR, Types.VARCHAR };
		
		if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
			return 1;
		}else{
			return -1;
		} 		
	}
	
	
	
	
	public Object[] addToObjectArray(Object[] list, Object object) {

		Object[] newList = new Object[list.length + 1];

		for (int i = 0; i < list.length; i++) {
			newList[i] = list[i];
		}
		newList[list.length] = object;

		return newList;

	}

	public int[] addToIntArray(int[] list, int object) {

		int[] newList = new int[list.length + 1];

		for (int i = 0; i < list.length; i++) {
			newList[i] = list[i];
		}
		newList[list.length] = object;

		return newList;

	}
	
	public Integer updateDeveloperCert(Developer developer) {
		StringBuffer sql = new StringBuffer(); 
		Object[] parametros=new Object[0];
		int[] types=new int[0];
		
		sql.append("UPDATE developers ");
		sql.append("SET ");
		
		
		if(developer.getCertURL() != null){
			sql.append("certurl = ?");
			
			parametros = addToObjectArray(parametros, developer.getCertURL());
			types = addToIntArray(types, Types.VARCHAR);
		}

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		sql.append("WHERE username = ?");
		
		parametros = addToObjectArray(parametros, username);
		types = addToIntArray(types, Types.VARCHAR);

		System.out.println("jdbcTemplate "+jdbcTemplate.update(sql.toString(), parametros, types));
		if(jdbcTemplate.update(sql.toString(), parametros, types)==1){
			Developer dev= getDeveloperByUsername(username);
			createCert(developer.getCertURL(), dev.getKey());
			return 1;
		}else{
			return -1;
		} 		
	}
	
	
	public void createCert(String certUrl, String key){
		try{
			URL url= new URL(certUrl);
			URLConnection urlCon = url.openConnection();
	
			// Se obtiene el inputStream del fichero y se abre el fichero local.
			InputStream is = urlCon.getInputStream();
			System.out.println(Configuration.certURL+Configuration.NAME+"_aps_dev_"+key +".p12");
			FileOutputStream fos = new FileOutputStream(Configuration.getInstance().getProperty(Configuration.certURL)+Configuration.NAME+"_aps_dev_"+key +".p12");
		  	// Lectura del fichero de la web y escritura en fichero local
			byte[] array = new byte[1000]; // buffer temporal de lectura.
			int leido = is.read(array);
			while (leido > 0) {
				fos.write(array, 0, leido);
				leido = is.read(array);
			}
	
			// cierre de conexion y fichero.
			is.close();
			fos.close();
		}catch(Exception e){
			System.out.print("Exception "+e);
		}
	}
}
