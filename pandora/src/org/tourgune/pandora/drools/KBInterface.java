package org.tourgune.pandora.drools;

import org.drools.runtime.StatefulKnowledgeSession;
import org.tourgune.pandora.bean.User;

public interface KBInterface {
	
	public void scheduleGC();
	
	public void scheduleReasoning();
	
	public void saveOrUpdateObject(Object fact);
	
	public void saveOrUpdateUser(User user);
	
	public void saveOrUpdateUserIBeacon(User user);
	
	public void deleteObject(Object fact);
	
	public StatefulKnowledgeSession getKsession();
	 

}
