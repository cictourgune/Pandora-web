package org.tourgune.pandora.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.tourgune.pandora.bean.Envios;


public class ShipmentUsersRowMapper implements RowMapper<Envios> {

	public Envios mapRow(ResultSet rs, int index) throws SQLException {

		Envios shipment = new Envios();

		shipment.setFecha(rs.getString("fecha"));
		shipment.setDiffusers(rs.getInt("diffusers"));
		shipment.setCantidad(rs.getInt("cantidad"));
		
		return shipment;
		
	}

}
