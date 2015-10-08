package org.tourgune.pandora.drools;
 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.AssertBehaviorOption;
import org.drools.conf.EventProcessingOption;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.tourgune.pandora.bean.Rule;
import org.tourgune.pandora.bean.Situation;
import org.tourgune.pandora.bean.User;
import org.tourgune.pandora.dao.FenceDao;
import org.tourgune.pandora.drools.utils.QueueProcessing;
import org.tourgune.pandora.drools.utils.RuleUtils;
import org.tourgune.pandora.facade.RuleFacade;
import org.tourgune.pandora.util.Configuration;

@Service
public class KB implements InitializingBean, KBInterface{
	 
	private static final Logger logger = Logger.getLogger(KB.class);
	
	
	
	
	@Resource
	private FenceDao fenceDao;	
	@Resource
	private RuleFacade ruleFacade;
	
	private StatefulKnowledgeSession ksession;
	private KnowledgeBase kbase;
	private Boolean autoFireRules = false;
	
	@Resource
	private QueueProcessing queueProcessing; 
	private BlockingQueue<Situation> sharedQueue = new LinkedBlockingQueue<Situation>();
	
	
	public KB(){
		initKB(); 
	}
	
	//------------------------------------------------------------------------- init ----------------------------------------
	
	public void initKB(){ 
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(); 
		kbuilder.add( ResourceFactory.newClassPathResource( "org/tourgune/"+Configuration.NAME+"/drools/drl/rules.drl", getClass() ), ResourceType.DRL ); 
		if ( kbuilder.hasErrors() ) {
		    System.err.println( kbuilder.getErrors().toString() );
		}

		KnowledgeBaseConfiguration kbaseConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		kbaseConfig.setOption(AssertBehaviorOption.EQUALITY); //TODO ver para que se pone esto
		kbaseConfig.setOption( EventProcessingOption.STREAM);  //para procesar eventos temporales
		
		kbase = KnowledgeBaseFactory.newKnowledgeBase(kbaseConfig);  
		kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
		ksession = kbase.newStatefulKnowledgeSession(); 
		logger.info("KB initialized");
	}
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		// Cargar reglas de DB a la KB
		List listaReglas = ruleFacade.getAllRules(); 
		for(int i=0;i<listaReglas.size();i++){
			saveOrUpdateObject(listaReglas.get(i));
		}	
		logger.info("KB: "+listaReglas.size()+" rules loaded");
		ksession.setGlobal("utils", new RuleUtils(sharedQueue)); 
		queueProcessing.setSharedQueue(sharedQueue);
		queueProcessing.start();  
	} 
	 
	//------------------------------------------------------------ Garbage Collector -----------------------------------------------------------
	
	@Scheduled(fixedDelay=Constants.GC_PERIOD)
	public void scheduleGC(){
		executeGC();
	}  
	
	@Async
	public void executeGC(){ 
		//borrar usuarios
		QueryResults results = ksession.getQueryResults("getUsers");
		for ( QueryResultsRow row : results ) {
			Object o = row.get( "$user"); 
			try{
				if(o instanceof User){ 
					Calendar updated = ((User) o).getUpdated();  
					if(Calendar.getInstance().getTimeInMillis()-updated.getTimeInMillis()>Constants.GC_PERIOD){
						///borrar objeto 
						deleteObject(o);  
						//logger.info("Deleting object: "+o);
						
					}  
				} 
			}catch(Exception e){  
			}   
		}  
		logger.info("KB GC of outdated users executed");
		
		//borrar reglas con fecha caduca
		results = ksession.getQueryResults("getRules");
		for ( QueryResultsRow row : results ) {
			Object o = row.get( "$rule"); 
			try{
				if(o instanceof Rule){  
					deleteObject(o);   
				} 
			}catch(Exception e){  
			}   
		}
		logger.info("KB GC of outdated rules executed");
	}
	
	//----------------------------------------------------------------------------- API -------------------------------------------------------------------
	
	public void deleteObject(Object fact){
		if (fact == null) {
			return;
		}
		FactHandle factHandle = ksession.getFactHandle(fact);
		if (factHandle != null) { 
			ksession.retract(factHandle);  
			logger.debug("KB object deleted");
			if(autoFireRules){
				fireAllRules();
			} 
		} 
	}
	
	public Object getObject(Object o){
		FactHandle factHandle = ksession.getFactHandle(o);
		if (factHandle == null) {			 
			return null;
		} else {
			return ksession.getObject(factHandle);			 
		}
	}
	
	public void fireAllRules(){   
		ksession.fireAllRules();    
	}

	
	public void saveOrUpdateObject(Object fact) { 
		if (fact == null) {
			return;
		} 
		FactHandle factHandle = ksession.getFactHandle(fact);
		if (factHandle == null) {				
			ksession.insert(fact);
			logger.debug("KB object inserted");
		} else {   
			ksession.update(factHandle, fact);
			logger.debug("KB object updated");
		} 
		if(autoFireRules){
			fireAllRules();
		}   
	}
	
	public void saveOrUpdateUser(User user){
		if (user == null) {
			return;
		}  
		List listNewFenceId = fenceDao.getFenceList(user.getLatitude(), user.getLongitude(), user.getDev_key());
		//logger.debug("KB processing user new fences: "+listNewFenceId);
	 
		FactHandle factHandle = ksession.getFactHandle(user);
		if (factHandle == null) {	 
			user.setFence_id_list(listNewFenceId);
			user.setUpdated(Calendar.getInstance());
			ksession.insert(user);
			logger.debug("KB inserting user with current fences: "+user.getFence_id_list()+" and past fences: "+user.getPre_fence_id_list()); 
		} else {  
		 
			User userKB = (User) getObject(user); 
			//ver si ha cambiado el valor actual respecto al valor en KB 
			ArrayList aux_area_id_list = new ArrayList();			
			aux_area_id_list.addAll(userKB.getFence_id_list());//copiar las current fences que tenia en KB 
			ArrayList aux_new_area_id_list = new ArrayList();			
			aux_new_area_id_list.addAll(listNewFenceId);//copiar las current nuevas fences
			
//			System.out.println("aux_area_id_list "+aux_area_id_list);
//			System.out.println("aux_new_area_id_list "+aux_new_area_id_list);
			//ver si son iguales o hay diferencias 
			Integer difference;
			if(aux_new_area_id_list.size()>=aux_area_id_list.size()){
				aux_new_area_id_list.removeAll(aux_area_id_list);
				difference = aux_new_area_id_list.size();
			}else{
				aux_area_id_list.removeAll(aux_new_area_id_list);
				difference = aux_area_id_list.size();
			}  
//			System.out.println("diff "+ difference);
			if(difference>0){ //si hay cambios 
				//hacer el cambio de fences a previous 
				//borrar areas que siguen estando en las actuales  
				List aux_userkb_area_list = userKB.getFence_id_list();
//				System.out.println("aux_userkb_area_list "+aux_userkb_area_list);
				aux_userkb_area_list.removeAll(listNewFenceId);
//				System.out.println("aux_userkb_area_list "+aux_userkb_area_list);
				userKB.setFence_id_list(listNewFenceId);
				userKB.setPre_fence_id_list(aux_userkb_area_list); 
				userKB.setUpdated(Calendar.getInstance());
				ksession.update(factHandle, userKB);
				logger.debug("KB updating user with current fences: "+userKB.getFence_id_list()+" and past fences: "+userKB.getPre_fence_id_list()); 
			} 
		} 
		if(autoFireRules){
			fireAllRules();
		}   
	}
	
	
	public void saveOrUpdateUserIBeacon(User user){
		if (user == null) {
			return;
		}  
		
		System.out.println("LLEGA "+user.getIbeacon_id_list());
		
		//En base a los ID de los IBeacon, obtener la lista de fences correspondientes a dichos iBeacon
		fenceDao.getFenceListIBeacon(user.getIbeacon_id_list(), user.getDev_key());
		
		List listNewFenceIbeaconId = fenceDao.getFenceListIBeacon(user.getIbeacon_id_list(), user.getDev_key());
		//logger.debug("KB processing user new fences: "+listNewFenceId);
	 
		FactHandle factHandle = ksession.getFactHandle(user);
		if (factHandle == null) {	 
			user.setFence_ibeacon_id_list(listNewFenceIbeaconId);
			user.setUpdated(Calendar.getInstance());
			ksession.insert(user);
			logger.debug("KB inserting user with current fences ibeacon: "+user.getFence_ibeacon_id_list()+" and past fences ibeacon: "+user.getPre_fence_ibeacon_id_list()); 
		} else {  
		 
			User userKB = (User) getObject(user); 
			//ver si ha cambiado el valor actual respecto al valor en KB 
			ArrayList aux_area_id_list = new ArrayList();			
			aux_area_id_list.addAll(userKB.getFence_ibeacon_id_list());//copiar las current fences que tenia en KB 
			ArrayList aux_new_area_id_list = new ArrayList();			
			aux_new_area_id_list.addAll(listNewFenceIbeaconId);//copiar las current nuevas fences
			
//			System.out.println("aux_area_id_list "+aux_area_id_list);
//			System.out.println("aux_new_area_id_list "+aux_new_area_id_list);
			//ver si son iguales o hay diferencias 
			Integer difference;
			if(aux_new_area_id_list.size()>=aux_area_id_list.size()){
				aux_new_area_id_list.removeAll(aux_area_id_list);
				difference = aux_new_area_id_list.size();
			}else{
				aux_area_id_list.removeAll(aux_new_area_id_list);
				difference = aux_area_id_list.size();
			}  
//			System.out.println("diff "+ difference);
			if(difference>0){ //si hay cambios 
				//hacer el cambio de fences a previous 
				//borrar areas que siguen estando en las actuales  
				List aux_userkb_area_list = userKB.getFence_ibeacon_id_list();
//				System.out.println("aux_userkb_area_list "+aux_userkb_area_list);
				aux_userkb_area_list.removeAll(listNewFenceIbeaconId);
//				System.out.println("aux_userkb_area_list "+aux_userkb_area_list);
				userKB.setFence_ibeacon_id_list(listNewFenceIbeaconId);
				userKB.setPre_fence_ibeacon_id_list(aux_userkb_area_list); 
				userKB.setUpdated(Calendar.getInstance());
				ksession.update(factHandle, userKB);
				logger.debug("KB updating user with current fences ibeacon: "+userKB.getFence_ibeacon_id_list()+" and past fences ibeacon: "+userKB.getPre_fence_ibeacon_id_list()); 
			} 
		} 
		if(autoFireRules){
			fireAllRules();
		}   
	}
	
	
	
	//-------------------------------------------------------------------- Reasoning ---------------------------------------------------------------
  
	@Scheduled(fixedDelay=Constants.REASONING_PERIOD)
	public void scheduleReasoning(){  
		ksession.fireAllRules();
	}

	public StatefulKnowledgeSession getKsession() {
		return ksession;
	}

	public void setKsession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

}
