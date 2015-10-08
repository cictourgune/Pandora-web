package org.tourgune.pandora.facade;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.pandora.bean.User;
import org.tourgune.pandora.dao.DeveloperDao;
import org.tourgune.pandora.dao.UserDao;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserFacade {
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private DeveloperDao developerDao;
	
	public Integer insertUser(User user){
		
		Integer devId = developerDao.getDeveloperIdByKey(user.getDev_key());
		if(!userDao.existsUser(user.getId(), devId)){
			return userDao.insertUser(user.getId(), devId); 
		} 
		return -1;
	}
	
	public Boolean existsUserRule(Integer userId, Integer ruleId){
		return userDao.existsUserRule(userId, ruleId);
	}
	
	public Integer insertUserRule(Integer userId, Integer ruleId){
		//obtener id de ese usuario
		return userDao.insertUserRule(userId, ruleId);
	}
	
	public Integer getUserDBId(Integer devId, String userId){
		return userDao.getUserDBId(devId, userId);
	}
	
	 
}
