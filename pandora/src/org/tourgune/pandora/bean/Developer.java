package org.tourgune.pandora.bean;

public class Developer {
	
	private Integer id;
	private String username;
	private String password;
	private String key;
	private String email;
	private boolean active;
	private String endpoint;
	private String certURL;

	 
	 public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	 
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getCertURL() {
		return certURL;
	}
	public void setCertURL(String certURL) {
		this.certURL = certURL;
	}
 
}
