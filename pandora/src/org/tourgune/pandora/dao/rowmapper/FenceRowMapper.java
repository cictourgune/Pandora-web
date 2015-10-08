package org.tourgune.pandora.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.pandora.bean.Fence;
import org.tourgune.pandora.bean.Rule;


public class FenceRowMapper implements RowMapper<Fence> {

	public Fence mapRow(ResultSet rs, int index) throws SQLException {

		Fence fence = new Fence();

		fence.setId(rs.getInt("id"));
		fence.setName(rs.getString("nombre"));
		
		fence.setRadius(rs.getDouble("radio"));
		fence.setCentreLatitude(rs.getDouble("latcentro"));
		fence.setCentreLongitude(rs.getDouble("longcentro"));
		fence.setIbeacon(rs.getString("ibeacon"));
		
		

		String data = (rs.getObject("astext").toString());
		data = data.substring(7); // quitar POLYGON y parentesis
		data = data.substring(2, data.length() - 2);
		fence.setGeometryString(data);

		// geometry to latlng
		StringTokenizer latlongpairs = new StringTokenizer(data, ",");

		while (latlongpairs.hasMoreTokens()) {
			String token = latlongpairs.nextToken(); // pareja lat long
														// separados por espacio
			StringTokenizer latlongtokens = new StringTokenizer(token, " ");
			Double latToken = Double.parseDouble(latlongtokens.nextToken());
			Double longToken = Double.parseDouble(latlongtokens.nextToken());
			fence.getLatlngList().add(latToken);
			fence.getLatlngList().add(longToken);
		}
		
		try{
			Integer statusFence = rs.findColumn("statusfence");
			Rule rule = new Rule(); 
			rule.setStatus_fence(rs.getInt("statusfence"));
			fence.setRule(rule); 
			fence.getRule().setDesde(rs.getDate("desde"));
			fence.getRule().setHasta(rs.getDate("hasta"));
			fence.getRule().setFromDate(rs.getDate("desde").toString());
			fence.getRule().setToDate(rs.getDate("hasta").toString());
			
			return fence;
		}catch(Exception e){
			return fence;
		} 
	}

}
