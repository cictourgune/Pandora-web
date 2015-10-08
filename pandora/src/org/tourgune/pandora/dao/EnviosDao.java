package org.tourgune.pandora.dao;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.tourgune.pandora.bean.Envios;
import org.tourgune.pandora.dao.rowmapper.ShipmentAreasRowMapper;
import org.tourgune.pandora.dao.rowmapper.ShipmentUsersRowMapper;

@Service("enviosDao")
public class EnviosDao {
	
	@Resource
	private DeveloperDao developerDao;
	
	public Boolean insertEnvio(Integer fenceId, Date fecha, String userId){
		
		StringBuffer sql = new StringBuffer(); 
		Object[] parametros;
		int[] types;
		
	 
		sql.append("INSERT INTO ");
		sql.append("envios ");
		sql.append("(fenceid, fecha, userid) ");
		sql.append("VALUES ");
		sql.append("(?, ?, ?)");

		parametros = new Object[] { fenceId, fecha, userId};
		types = new int[] { Types.INTEGER, Types.DATE, Types.VARCHAR};

		return jdbcTemplate.update(sql.toString(), parametros, types)==1;   
	}
	
	
	public List<Envios> getListaEnviosAreasFecha(String query, String date_from, String date_until) {
		String consultaCompleta = "";
		System.out.println("query"+query);
		System.out.println("date_from"+date_from);
		System.out.println("date_until"+date_until);
		
		consultaCompleta = "select to_char(a.day, 'YYYY-MM-DD') as fecha,a.fences as fenceid,coalesce(b.cantidad, 0) as cantidad, a.fencename " +
						   "from (select d as day, enviosfences.fenceid as fences, enviosfences.nombre as fencename  " +
						   		 "from generate_series("+date_from+", "+date_until+", '1 day'::interval) d " +
						   		 "cross join (select fenceid,nombre from envios, fences where envios.fenceid=fences.id) enviosfences " +
						   		 "where enviosfences.fenceid in ("+query+") " +
						   		 "group by day, enviosfences.fenceid, enviosfences.nombre order by day,enviosfences.fenceid) as a " +
			   			   "LEFT JOIN (select d as day, count(enviosfences.fenceid) as cantidad, enviosfences.fenceid as fences " +
			   			   			  "from generate_series("+date_from+", "+date_until+", '1 day'::interval) d " +
			   			   			  "left join (select fenceid,nombre, envios.fecha as fecha from envios, fences where envios.fenceid=fences.id) enviosfences on date(enviosfences.fecha) = d " +
			   			   			  "and enviosfences.fenceid in ("+query+") " +
			   			   			  "group by day, enviosfences.fenceid order by day) as b " +
   			   			  "on a.day=b.day " +
   			   			  "and a.fences=b.fences";
		

		List<Envios> listaShipment = null;
		try {

			listaShipment = (List<Envios>) jdbcTemplate.query(consultaCompleta,	new ShipmentAreasRowMapper());
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}

		return listaShipment;
	
	}
	public List<Envios> getListaEnviosUsuariosFecha(String query, String date_from, String date_until) {
		String consultaCompleta = "";
		System.out.println("query"+query);
		System.out.println("date_from"+date_from);
		System.out.println("date_until"+date_until);
		
		consultaCompleta = "select to_char(a.day, 'YYYY-MM-DD') as fecha,coalesce(b.users, 0) as diffusers,coalesce(b.cantidad, 0) as cantidad " +
						   "from (select d as day from generate_series("+date_from+", "+date_until+", '1 day'::interval) d " +
						   		 "cross join envios " +
						   		 "where envios.fenceid in ("+query+") " +
						   		 "group by day " +
						   		 "order by day) as a " +
				   		   "LEFT JOIN (select d as day, count(distinct(userid)) as users, count(envios.fenceid) as cantidad " +
				   		   			  "from generate_series("+date_from+", "+date_until+", '1 day'::interval) d " +
				   		   			  "left join envios on date(envios.fecha) = d " +
				   		   			  "and envios.fenceid in ("+query+") " +
				   		   			  "group by day order by day) as b " +
	   		   			   "on a.day=b.day ";
		


		List<Envios> listaShipment = null;
		try {

			listaShipment = (List<Envios>) jdbcTemplate.query(consultaCompleta,	new ShipmentUsersRowMapper());
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}

		return listaShipment;
	
	}
 
	
	//-------------------------------------------------
	
	@Resource
	private JdbcTemplate jdbcTemplate;


 
}
