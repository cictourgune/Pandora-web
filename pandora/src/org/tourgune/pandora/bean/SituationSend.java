package org.tourgune.pandora.bean;

import java.util.HashMap;
 
public class SituationSend {
	
	private Integer fence_id;
	private String fence_name;
	private Integer fence_status; 
	private String user_id;
	private String gcm_id;
	private String data;
	private HashMap extras; 
	
	private Integer period_fence; 
	private String fromDate;
	private String toDate;  
	private Integer fromHour;
	private Integer toHour;
 
	
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getFence_name() {
		return fence_name;
	}
	public void setFence_name(String fence_name) {
		this.fence_name = fence_name;
	}
 
	public Integer getFence_status() {
		return fence_status;
	}
	public void setFence_status(Integer fence_status) {
		this.fence_status = fence_status;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getGcm_id() {
		return gcm_id;
	}
	public void setGcm_id(String gcm_id) {
		this.gcm_id = gcm_id;
	}
	public HashMap getExtras() {
		return extras;
	}
	public void setExtras(HashMap extras) {
		this.extras = extras;
	}
	public Integer getFence_id() {
		return fence_id;
	}
	public void setFence_id(Integer fence_id) {
		this.fence_id = fence_id;
	}
	public Integer getPeriod_fence() {
		return period_fence;
	}
	public void setPeriod_fence(Integer period_fence) {
		this.period_fence = period_fence;
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
