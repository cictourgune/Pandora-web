package org.tourgune.pandora.bean;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Rule {
	@JsonIgnore
	private Integer id;
	@JsonIgnore
	private Integer fence_id; //una regla pertenece a una sola fence
	@JsonIgnore
	private String fence_name;
	private Integer status_fence; //1 entra, 0 sale
	private Integer period_fence; //1 primera vez, 0 siempre
	@JsonIgnore
	private String dev_key; //developer key 

	//fechas en las reglas	
	@JsonIgnore
	private Date desde; 
	@JsonIgnore
	private Date hasta;
	
	//datos a ser enviados cuando se cumple la regla
	private String data; 
	//fechas desde-hasta en formato String 
	private String fromDate="";
	private String toDate=""; 
	
	//horas 
	private Integer fromHour;
	private Integer toHour;
  
	private Integer destination; //1 movil, 0 endpoint
	
	public Rule(){
		//inicializar fechas para que siempre se cumplan las condiciones de la regla si es que no tienen valor asignado
		Calendar desdeCalendar = Calendar.getInstance();
		desdeCalendar.set(Calendar.YEAR, 2000);
		desde = desdeCalendar.getTime(); 
		
		Calendar hastaCalendar = Calendar.getInstance();
		hastaCalendar.set(Calendar.YEAR, 3000);
		hasta = hastaCalendar.getTime();
		
		//inicializar horas para que siempre se cumplan las condiciones de la regla si es que no tienen valor asignado
		fromHour = 0;
		toHour = 24; 	
		
	} 

	//------------------------------------------------EQ&HC--------------------------------------------
 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else 
			if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
	//------------------------------------------------G&S-------------------------------------------- 

	
	@Override
	public int hashCode() {		 
		return (this.getClass().toString()+id).hashCode();
	}
 
  

	public Integer getFence_id() {
		return fence_id;
	}

	public void setFence_id(Integer fence_id) {
		this.fence_id = fence_id;
		
	} 

	 
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getDev_key() {
		return dev_key;
	}

	public void setDev_key(String dev_key) {
		this.dev_key = dev_key;
	}

 
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getStatus_fence() {
		return status_fence;
	}

	public void setStatus_fence(Integer status_fence) {
		this.status_fence = status_fence;
	}

 
	public Date getDesde() {
		return desde;
	}

	public void setDesde(Date desde) {
		this.desde = desde;
	}

	public Date getHasta() {
		return hasta;
	}

	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFence_name() {
		return fence_name;
	}

	public void setFence_name(String fence_name) {
		this.fence_name = fence_name;
	}

	 

	public Integer getDestination() {
		return destination;
	}

	public void setDestination(Integer destination) {
		this.destination = destination;
	}

	public Integer getPeriod_fence() {
		return period_fence;
	}

	public void setPeriod_fence(Integer period_fence) {
		this.period_fence = period_fence;
	}

	public Integer getFromHour() {
		return fromHour;
	}

	public void setFromHour(Integer fromHour) {
		this.fromHour = fromHour;
	}

	public Integer getToHour() {
		return toHour;
	}

	public void setToHour(Integer toHour) {
		this.toHour = toHour;
	}
 
	
 
}
