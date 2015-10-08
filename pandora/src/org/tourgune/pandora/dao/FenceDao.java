package org.tourgune.pandora.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.tourgune.pandora.bean.Fence;
import org.tourgune.pandora.dao.rowmapper.FenceRowMapper;

@Service("fenceDao")
public class FenceDao {
	
	@Resource
	private DeveloperDao developerDao;
	
	public List getFenceListIBeacon(List iBeaconID, String devkey){
		
		List listaFences = new ArrayList();
		
		StringBuffer sql = new StringBuffer();
		Object[] parametros;
		int[] types;
		
		String rangeParam = "";
		
		if(!iBeaconID.isEmpty()){
			Integer devid = developerDao.getDeveloperIdByKey(devkey);
			for(int i=0;i<iBeaconID.size();i++){
				String idFence = (String) iBeaconID.get(i).toString();
				if(i==iBeaconID.size()-1){
					rangeParam = rangeParam + "'"+idFence+"'::varchar";
				}else{
					rangeParam = rangeParam + "'"+idFence+"'::varchar,";
				}
			}
			
			sql.append("SELECT  id ");
			sql.append("FROM ");
			sql.append("\"fences\" ");
			sql.append("WHERE ");
			sql.append("ibeacon IN (");
			sql.append(rangeParam);
			sql.append(")");
			sql.append(" AND developerid = ? ");
			
			parametros = new Object[] { devid };
			types = new int[] { Types.INTEGER};

			listaFences = jdbcTemplate.queryForList(sql.toString(), parametros, types, Integer.class ); 
		}
	 
		return listaFences;	
	}
	
	public Fence getFence(Integer idfence){
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT id, nombre, radio, latcentro, longcentro, AsText(poligono), ibeacon FROM ");
		sql.append("\"fences\" WHERE id=?"); 
		Fence fence = null;
		try {
			
			Object[] parametros;
			parametros = new Object[] { idfence };
			fence = (Fence) jdbcTemplate.queryForObject(sql.toString(),  parametros, new FenceRowMapper());

		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
		return fence;
	}
	
	public List getFenceList(Integer developerId){
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT f.id, nombre, radio, latcentro, longcentro, AsText(poligono), desde, hasta, statusfence, ibeacon FROM ");
		sql.append("\"fences\" AS f, \"reglas\" AS r  "); 
		sql.append("WHERE developerid=? AND f.id=r.fenceid "); 
		
		Object[] parametros;
		int[] types;
		
		parametros = new Object[] { developerId };
		types = new int[] { Types.INTEGER};
		
		List listArea = null;
		try {
			listArea = (List) jdbcTemplate.query(sql.toString(),  parametros, types, new FenceRowMapper());

		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
		return listArea;
	}
	
	public List getFenceList(Double latitude, Double longitude, String devkey){ 
		Integer devid = developerDao.getDeveloperIdByKey(devkey);
		return getFenceList(latitude, longitude, devid);	
	}
	
	public List getFenceList(Double latitude, Double longitude, Integer devid){
		
		StringBuffer sql = new StringBuffer();
		Object[] parametros;
		int[] types;
		
		sql.append("SELECT  id ");
		sql.append("FROM ");
		sql.append("\"fences\" ");
		sql.append("WHERE ");
		sql.append("ST_Within(st_makepoint("+latitude+","+longitude+")"+",poligono) = true AND developerid = ?  AND ibeacon IS NULL");
		
		parametros = new Object[] { devid };
		types = new int[] { Types.INTEGER};
	 	
		List listaFences = jdbcTemplate.queryForList(sql.toString(), parametros, types, Integer.class ); 
		
		return listaFences;	
	}
	
	public Boolean insertFence(String nombre, String geometryString, Double latCentro, Double longCentro, Double radio, Integer developerId, String ibeacon){
		
		StringBuffer sql = new StringBuffer(); 
		Object[] parametros;
		int[] types;
		
		if(ibeacon.trim().isEmpty()){
			ibeacon=null;
		}else{
			ibeacon.trim();
		}
 
		sql.append("INSERT INTO ");
		sql.append("fences ");
		sql.append("(nombre, poligono, latCentro, longCentro, radio, developerid, ibeacon) ");
		sql.append("VALUES ");
		sql.append("(?, GeometryFromText('Polygon((" + geometryString + "))'), ?,?,?, ?, ?)");

		parametros = new Object[] { nombre, latCentro, longCentro, radio, developerId, ibeacon };
		types = new int[] { Types.VARCHAR, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.INTEGER, Types.VARCHAR};

		return jdbcTemplate.update(sql.toString(), parametros, types)==1;   
	}
	
	
	public boolean updateFence(Integer fenceId, String nombre, String geometryString, Double latCentro, Double longCentro, Double radio, String ibeacon) {
		
		StringBuffer sql = new StringBuffer(); 
		Object[] parametros;
		int[] types;
		
		if(ibeacon.trim().isEmpty()){
			ibeacon=null;
		}else{
			ibeacon.trim();
		}
		
		sql.append("UPDATE fences ");
		sql.append("SET ");
		sql.append("nombre=?, poligono=GeometryFromText('Polygon((" + geometryString + "))'), latCentro=?, longCentro=?, radio=?, ibeacon=? ");
		sql.append("WHERE id = ?");
		
		parametros = new Object[] { nombre, latCentro, longCentro, radio, ibeacon, fenceId };
		types = new int[] { Types.VARCHAR, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.VARCHAR, Types.INTEGER};
		
		return jdbcTemplate.update(sql.toString(), parametros, types)==1;
	}
	
	
	public Boolean deleteFence(Integer id){
		StringBuffer sql = new StringBuffer();

		sql.append("DELETE FROM ");
		sql.append("\"fences\" ");
		sql.append("WHERE id = ?");

		Object[] parametros;
		int[] types;

		parametros = new Object[] { id };
		types = new int[] { Types.INTEGER };

		return jdbcTemplate.update(sql.toString(), parametros, types)==1; 
	}
	
	 
	public boolean isUniqueFenceName(Integer fenceId, String nombre, Integer devId) { 

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT COUNT(*) FROM ");
		sql.append("\"fences\" ");
		sql.append("WHERE nombre=? AND developerid=? AND id <> ?");

		Object[] parametros;
		parametros = new Object[] { nombre, devId, fenceId };
 
		Integer num = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
		return num==0;
	}
	
	
	//-----------------------------------------------------------
 
	public boolean isUniqueFenceName(String nombre) { 

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT COUNT(*) FROM ");
		sql.append("\"fences\" ");
		sql.append("WHERE nombre=?");

		Object[] parametros;
		parametros = new Object[] { nombre };
 
		Integer num = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
		return num==0;
	}
	
	
	public Integer getFenceId(String nombre, Integer devId) { 

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT id FROM ");
		sql.append("\"fences\" ");
		sql.append("WHERE nombre=? AND developerid=?");

		Object[] parametros;
		parametros = new Object[] { nombre, devId };
 
		Integer id = jdbcTemplate.queryForObject(sql.toString(), parametros, Integer.class);
		
		return id;
	}
	

	
	
	 
	
	
	
	
 
	
	//-------------------------------------------------
	
	@Resource
	private JdbcTemplate jdbcTemplate;


 
}
