package org.tourgune.pandora.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Fence {
	
	private Integer id;	 
	private String name; 
	private String ibeacon;
	@JsonIgnore
	private String geometryString;  
	private Double centreLatitude;
	private Double centreLongitude;
	private Double radius;
	
	@JsonIgnore
	private List<Double> latlngList; 
	@JsonIgnore
	private Developer developer;
	
	private Rule rule;
	
	public Fence(){ 
		latlngList = new ArrayList<Double>();
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGeometryString() {
		return geometryString;
	}

	public void setGeometryString(String geometryString) {
		this.geometryString = geometryString;
	}

	public List<Double> getLatlngList() {
		return latlngList;
	}

	public void setLatlngList(List<Double> latlngList) {
		this.latlngList = latlngList;
	} 
//	public Double getRadio() {
//		return radio;
//	}
//
//	public void setRadio(Double radio) {
//		this.radio = radio;
//	}

	public Double getCentreLatitude() {
		return centreLatitude;
	}

	public void setCentreLatitude(Double centreLatitude) {
		this.centreLatitude = centreLatitude;
	}

	public Double getCentreLongitude() {
		return centreLongitude;
	}

	public void setCentreLongitude(Double centreLongitude) {
		this.centreLongitude = centreLongitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public String getIbeacon() {
		return ibeacon;
	}

	public void setIbeacon(String ibeacon) {
		this.ibeacon = ibeacon;
	}

//	public String getNombre() {
//		return nombre;
//	}
//
//	public void setNombre(String nombre) {
//		this.nombre = nombre;
//	}

}
