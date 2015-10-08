package org.tourgune.pandora.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.pandora.bean.Developer;
import org.tourgune.pandora.bean.Fence;
import org.tourgune.pandora.bean.Rule;
import org.tourgune.pandora.dao.FenceDao;
import org.tourgune.pandora.dao.RuleDao;
import org.tourgune.pandora.dao.UserDao;
import org.tourgune.pandora.drools.KBInterface;
import org.tourgune.pandora.util.FenceUtils;

@Service("fenceFacade")
@Transactional(propagation = Propagation.REQUIRED)
public class FenceFacade {
	
	@Resource
	private FenceDao fenceDao;
	@Resource
	private RuleDao ruleDao;
	@Resource
	private UserDao userDao;
	@Resource
	private KBInterface kb;
	@Resource
	private DeveloperFacade developerFacade;
	
	public Integer insertFence(Fence fence){
		
		if(!fence.getRule().getFromDate().isEmpty() && !fence.getRule().getToDate().isEmpty()){
			
			SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");
			 try {
				 
				 fence.getRule().setDesde(formatoDate.parse(fence.getRule().getFromDate()));
				 
				 //la fecha del hasta hay que configurarla a última hora del día 23:59 porque sino en la comparación de la regla (new Date() <= hasta), no salta nunca si desde y hasta son la misma fecha!!
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(formatoDate.parse(fence.getRule().getToDate())); 
				 cal.set(Calendar.HOUR_OF_DAY, 23);
				 cal.set(Calendar.MINUTE, 59);
				 cal.set(Calendar.SECOND, 59);  
//				 fence.getRule().setHasta(formatoDate.parse(fence.getRule().getToDate()));
				 fence.getRule().setHasta(cal.getTime());
			} catch (ParseException e) { 
				e.printStackTrace();
			} 
		} 
			   
		//insertar fence
		if(fenceDao.insertFence(fence.getName(), FenceUtils.getGeometryStringFromCircle(fence.getCentreLatitude(),fence.getCentreLongitude(), fence.getRadius()), fence.getCentreLatitude(), fence.getCentreLongitude(), fence.getRadius(), fence.getDeveloper().getId(), fence.getIbeacon())){
			//fence id 
			Integer fenceId = fenceDao.getFenceId(fence.getName(), fence.getDeveloper().getId());
			Developer developer = fence.getDeveloper();
			if(fence.getRule()!=null){
				//insertar regla
				if(ruleDao.insertRule(fence.getRule().getStatus_fence(), fenceId, developer.getKey(), fence.getRule().getDesde(), fence.getRule().getHasta(), fence.getRule().getData(), fence.getRule().getDestination(), fence.getRule().getPeriod_fence(), fence.getRule().getFromHour(), fence.getRule().getToHour())){
					//insertar en KB
					Rule rule = fence.getRule();
					rule.setFence_id(fenceId);
					rule.setDev_key(developer.getKey());
					rule.setId(ruleDao.getRuleByFence(fenceId).getId());
					rule.setFence_name(fence.getName());
					rule.setDesde(fence.getRule().getDesde());
					rule.setHasta(fence.getRule().getHasta());
					rule.setData(fence.getRule().getData()); 
					rule.setFromHour(fence.getRule().getFromHour());
					rule.setToHour(fence.getRule().getToHour());
					kb.saveOrUpdateObject(rule);
					return  fenceId;
				}
			} 
		} 
//		} 
		return -1; 
	}
	 
	public Integer updateFence(Fence fence) {
		 
		if(!fence.getRule().getFromDate().isEmpty() && !fence.getRule().getToDate().isEmpty()){
			
			SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");
			 try {
				 fence.getRule().setDesde(formatoDate.parse(fence.getRule().getFromDate()));
				 fence.getRule().setHasta(formatoDate.parse(fence.getRule().getToDate()));
			} catch (ParseException e) { 
				e.printStackTrace();
			}
		
		} 
			 
		//actualizar fence
		if(fenceDao.updateFence(fence.getId(), fence.getName(), FenceUtils.getGeometryStringFromCircle(fence.getCentreLatitude(),fence.getCentreLongitude(), fence.getRadius()), fence.getCentreLatitude(), fence.getCentreLongitude(), fence.getRadius(), fence.getIbeacon())){
			Integer fenceId = fence.getId();
			Developer developer = fence.getDeveloper();

			if(fence.getRule()!=null){
				//insertar regla
				if(ruleDao.updateRule(fenceId, fence.getRule().getStatus_fence(), fence.getRule().getDesde(), fence.getRule().getHasta(), fence.getRule().getData(), fence.getRule().getDestination(), fence.getRule().getPeriod_fence(),  fence.getRule().getFromHour(), fence.getRule().getToHour())){
//					//actualizar en KB
					Rule rule = fence.getRule();									
					rule.setFence_id(fenceId);
					rule.setDev_key(developer.getKey());
					rule.setId(ruleDao.getRuleByFence(fenceId).getId());
					rule.setFence_name(fence.getName());
					rule.setDesde(fence.getRule().getDesde());
					rule.setHasta(fence.getRule().getHasta()); 
					rule.setData(fence.getRule().getData()); 
					rule.setFromHour(fence.getRule().getFromHour());
					rule.setToHour(fence.getRule().getToHour());
					
					kb.saveOrUpdateObject(rule);
					//Borrar tuplas user_rule porque la regla ha cambiado y podrá volver a enviarse 
					userDao.deleteUserRule(ruleDao.getRuleByFence(fenceId).getId());
					
					return  fenceId;
				}
			} 
		} 
		
		return -1; 
	}
	
	public Fence getFence(Integer idfence){
		Fence fence = fenceDao.getFence(idfence);
		if(fence!=null){	 
			fence.setRule(ruleDao.getRuleByFence(idfence));
		} 
		return fence;
	}
	
	//borra regla asociada
	public Boolean deleteFence(Integer id){ 
		Rule rule = ruleDao.getRuleByFence(id); 
		if(fenceDao.deleteFence(id)){
			//borrar de KB
			kb.deleteObject(rule);
			return true;
		}
		return false; 
	}
	
	public List getFenceList(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Developer developer = developerFacade.getDeveloperByUsername(username);
		
		List fenceList = fenceDao.getFenceList(developer.getId());
		for(int i=0;i<fenceList.size();i++){
			Fence fence = (Fence) fenceList.get(i);
			if(fence!=null){	 
				fence.setRule(ruleDao.getRuleByFence(fence.getId()));
			} 			
		} 
		return fenceList;
	}
	
	public List getFenceList(Double latitude, Double longitude, String devkey){ 
		Integer devid = developerFacade.getDeveloperIdByKey(devkey);
		return fenceDao.getFenceList(latitude, longitude, devid);	
	}
  
	public boolean isUniqueFenceName(Integer fenceId, String nombre, Integer devId) { 
		return fenceDao.isUniqueFenceName(fenceId, nombre, devId);
	}
}
