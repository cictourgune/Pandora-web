package org.tourgune.pandora.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class User {
	
	private String  id; //id del usuario movil (IMEI por defecto, aunque puede ser custom)
	
	private double latitude;
	private double longitude;	
	private String dev_key;	
	private String gcmId;
	private String appleToken;
		
	private List fence_id_list;
	private List pre_fence_id_list;
	
	private List ibeacon_id_list; //V1.2 vector que almacena los id de los beacon que llega del móvil
	private List fence_ibeacon_id_list;
	private List pre_fence_ibeacon_id_list;
	
	private Calendar updated;//para GC
	
	//extra parameters in a hashmap
	private HashMap extras;
	
	 
	public User(){
		fence_id_list = new ArrayList();
		pre_fence_id_list = new ArrayList(); 
		extras = new HashMap();
		ibeacon_id_list = new ArrayList();
		fence_ibeacon_id_list = new ArrayList();
		pre_fence_ibeacon_id_list = new ArrayList();
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
		User other = (User) obj;
		if (id+dev_key == null) {
			if (other.getId()+other.getDev_key() != null)
				return false;
		} else 
			if (!(id+dev_key).equals(other.getId()+other.getDev_key()))
			return false;
		return true;
	}
	
	//------------------------------------------------G&S-------------------------------------------- 

	@Override
	public int hashCode() {		 
		return (this.getClass().toString()+id+dev_key).hashCode();
	}  
 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDev_key() {
		return dev_key;
	}

	public void setDev_key(String dev_key) {
		this.dev_key = dev_key;
	}

	public List getFence_id_list() {
		return fence_id_list;
	}

	public void setFence_id_list(List fence_id_list) {
		this.fence_id_list = fence_id_list;
	}

	public List getPre_fence_id_list() {
		return pre_fence_id_list;
	}

	public void setPre_fence_id_list(List pre_fence_id_list) {
		this.pre_fence_id_list = pre_fence_id_list;
	}
 
	public Calendar getUpdated() {
		return updated;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	} 

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public HashMap getExtras() {
		return extras;
	}

	public void setExtras(HashMap extras) {
		this.extras = extras;
	}

	public String getAppleToken() {
		return appleToken;
	}

	public void setAppleToken(String appleToken) {
		this.appleToken = appleToken;
	}

	public List getIbeacon_id_list() {
		return ibeacon_id_list;
	}

	public void setIbeacon_id_list(List ibeacon_id_list) {
		this.ibeacon_id_list = ibeacon_id_list;
	}

	public List getFence_ibeacon_id_list() {
		return fence_ibeacon_id_list;
	}

	public void setFence_ibeacon_id_list(List fence_ibeacon_id_list) {
		this.fence_ibeacon_id_list = fence_ibeacon_id_list;
	}

	public List getPre_fence_ibeacon_id_list() {
		return pre_fence_ibeacon_id_list;
	}

	public void setPre_fence_ibeacon_id_list(List pre_fence_ibeacon_id_list) {
		this.pre_fence_ibeacon_id_list = pre_fence_ibeacon_id_list;
	}
	 
	 

}
