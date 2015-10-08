package org.tourgune.pandora.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Este objeto se genera solamente en memoria, en el KB
 * 
 * @author David.Martin
 *
 */
public class Situation {
	
	private String id; //generada en la regla -> "in/out_"+$rule.getId()+$user.getId()
	private Rule rule;
	private User user;
	@JsonIgnore
	private boolean notified;

	private Object[] entities;
	
	public Situation(String id){
		this.id = id; 
		notified = false;
	}
	
	public Situation(String id, Rule rule, User user){
		this.id = id;
		this.rule = rule;
		this.user = user;
		notified = false;
	}


	public boolean isNotified() {
		return notified;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Object[] getEntities() {
		return entities;
	}


	public void setEntities(Object[] entities) {
		this.entities = entities;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Situation other = (Situation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
