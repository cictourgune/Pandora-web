package org.tourgune.pandora.facade;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.pandora.bean.Developer;
import org.tourgune.pandora.dao.DeveloperDao;
import org.tourgune.pandora.util.Configuration;
import org.tourgune.pandora.util.MailUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DeveloperFacade {
	
	@Resource
	private DeveloperDao developerDao;
	
	@Resource
	private MailUtils mailUtils;
	
	public static String DOMAIN = Configuration.getInstance().getProperty(Configuration.IP);
	public static String PORT = Configuration.getInstance().getProperty(Configuration.PORT);
	public static String ROOT = Configuration.getInstance().getProperty(Configuration.ROOT);
	
	public boolean existsDeveloperKey(String key){
		return developerDao.existsDeveloperKey(key); 
	}
	
	public Integer createDeveloper(Developer developer) { 
		String key = developerDao.createDeveloper(developer);
		if(key!="-1"){
			mailUtils.sendMail("pandora.platform@gmail.com",
						developer.getEmail().trim(),
		    		   "Welcome to Pandora!", 
		    		   "Hello "+developer.getUsername()+",\n\n"+
		    		   "\nplease press this link in order to start using the system \n\n " +
		    		   		"http://"+DOMAIN+":"+PORT+"/"+ROOT+"/validationKey?key="+key+"\n\n\n\n");  
		}
		return 1;   
	}
	 
	
	public String getDeveloperPassword(String username){
		return developerDao.getDeveloperPassword(username); 
	}
	
	public Integer getDeveloperIdByKey(String key){
		return developerDao.getDeveloperIdByKey(key); 
	}
	
	public Developer getDeveloperByUsername(String username){
		return developerDao.getDeveloperByUsername(username); 
	}
	
	public Integer existsUsername(String username, String email){
		return developerDao.existsUsername(username, email); 
	}
	
	public Integer activateDeveloper(String key) { 
		return developerDao.activateDeveloper(key);  
	}
	
	public Integer validateUser(String key){  
		//ver si existe
		if(developerDao.existsDeveloperKey(key)){
			//activar cuenta
			if (developerDao.activateDeveloper(key) != -1){ 
				return 1;
			}
		}
		return -1;
	}

	public Integer updateDeveloper(Developer developer) {
		return developerDao.updateDeveloper(developer);
	}
	public Integer updateDeveloperCert(Developer developer) {
		System.out.println("Dentro de updatefacade");
		return developerDao.updateDeveloperCert(developer);
	}

}
