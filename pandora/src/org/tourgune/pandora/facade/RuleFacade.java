package org.tourgune.pandora.facade;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.pandora.bean.Rule;
import org.tourgune.pandora.dao.RuleDao;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RuleFacade { 
	 
	@Resource
	private RuleDao ruleDao;
	
	public Boolean insertRule(Integer statusFence, Integer fenceId, String developerKey, Date desde, Date hasta, String url, Integer destinatario, Integer periodFence, Integer fromHour, Integer toHour){ 
		return ruleDao.insertRule(statusFence, fenceId, developerKey, desde, hasta, url, destinatario, periodFence, fromHour, toHour);
	} 
 
	public Rule getRuleByFence(Integer id){
		return ruleDao.getRuleByFence(id);
	}
	
	public List getAllRules(){ 
		return ruleDao.getAllRules();
	} 

}
