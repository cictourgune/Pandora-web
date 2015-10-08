package org.tourgune.pandora.drools.utils;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tourgune.pandora.bean.Developer;
import org.tourgune.pandora.bean.Situation;
import org.tourgune.pandora.bean.SituationSend;
import org.tourgune.pandora.dao.DeveloperDao;
import org.tourgune.pandora.dao.EnviosDao;
import org.tourgune.pandora.facade.UserFacade;
import org.tourgune.pandora.util.Configuration;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
 
@Service
public class QueueProcessing{
	
	private static final Logger logger = Logger.getLogger(QueueProcessing.class);
	  
	public BlockingQueue<Situation> sharedQueue;
	public static boolean stop = false;
//   
	@Resource
	private DeveloperDao developerDao;
	
	@Resource
	private EnviosDao enviosDao;
	
	@Resource
	private UserFacade userFacade;
	
	public BlockingQueue<Situation> getSharedQueue() {
		return sharedQueue;
	}

	public void setSharedQueue(BlockingQueue<Situation> sharedQueue) {
		this.sharedQueue = sharedQueue;  
		 
	}

	 
	@Async
	public void start() throws Exception { 
		while (!stop && sharedQueue!=null) {  
			try{ 
				final Object o = this.sharedQueue.take();
				if(o instanceof Situation){  
					sendData(o);  
				}  
			}		
			catch(Exception e){
				//e.printStackTrace(); 
			}  
		}  
	}
	
	@Async 
	public void sendData(Object o){
    	try { 
    		
    		Situation s = (Situation) o;
    		  
    		Integer devId = developerDao.getDeveloperIdByKey(s.getRule().getDev_key());
    		//coge el id de usuario numérico de la tabla 
    		Integer userId = userFacade.getUserDBId(devId, s.getUser().getId());
    		
    		if(s.getRule().getPeriod_fence()==1/*la primera vez solamente*/){
    			//comprobar si a ese usuario se le han enviado ya los datos asociados a esa regla
        		if(!userFacade.existsUserRule(userId, s.getRule().getId())){
        			//logger.debug("Primera vez: enviando primera vez"); 
        			//insertar 
        			userFacade.insertUserRule(userId, s.getRule().getId());
        			doSendData(userId, s);
        		}else{
        			//logger.debug("Primera vez: ya fue enviado, asi que NO enviar"); 
        		}
    		}else{ //siempre
    			//logger.debug("Siempre data"); 
    			doSendData(userId, s);
    		}    		
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	
	private void doSendData(Integer userId, Situation s){
	
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		HttpPost postRequest = null;
	 
		Developer dev = developerDao.getDeveloperByKey(s.getRule().getDev_key());
		
		SituationSend ss = new SituationSend();
		ss.setFence_id(s.getRule().getFence_id());
		ss.setFence_name(s.getRule().getFence_name());
		ss.setFence_status(s.getRule().getStatus_fence());
		ss.setUser_id(s.getUser().getId());
		ss.setGcm_id(s.getUser().getGcmId());
		ss.setExtras(s.getUser().getExtras());
		ss.setData(s.getRule().getData());
		 
		ss.setPeriod_fence(s.getRule().getPeriod_fence());
		ss.setFromDate(s.getRule().getFromDate());
		ss.setToDate(s.getRule().getToDate());
		ss.setFromHour(s.getRule().getFromHour());
		ss.setToHour(s.getRule().getToHour());
		 
		enviosDao.insertEnvio(ss.getFence_id(), new Date(), ss.getUser_id());
		 
		
		JSONObject j = JSONObject.fromObject( ss ); 
		String representacionJSON = j.toString();
		
	//	System.out.println("Sending data "+representacionJSON); 
		
//		System.out.println("representacionJSON: "+representacionJSON);
		//Enviar a servidor GCM o a ENDPOINT
		if(s.getRule().getDestination()==1){//movil //GCM with developer key
//			System.out.println("valor de gcmid: "+s.getUser().getGcmId());
//			System.out.println("valor de appleToken: "+s.getUser().getAppleToken());
			if(s.getUser().getGcmId()!=null){
				postRequest = new HttpPost("http://"+Configuration.getInstance().getProperty(Configuration.GCM_DOMAIN)+"/"+Configuration.getInstance().getProperty(Configuration.GCM_CONTEXT)+"/api/v1/device/"+s.getUser().getGcmId()+"/message?key="+s.getRule().getDev_key());
//				System.out.println("dentro del if del gcmid");
			}else{
				if(s.getUser().getAppleToken()!=null){
					postRequest=null;
//					System.out.println("dentro del if del appletoken");
					
					try {
			  
						ApnsService service =
							    APNS.newService()
							    .withCert(Configuration.getInstance().getProperty(Configuration.certURL)+Configuration.NAME+"_aps_dev_"+s.getUser().getDev_key() +".p12", s.getUser().getDev_key())
							    .withSandboxDestination()
							    .build();
				 
						
//						System.out.println("antes del payload");
						String payload = APNS.newPayload().alertBody(representacionJSON).badge(1).build();
	
						try{
							service.push(s.getUser().getAppleToken(), payload);
						}catch(Exception e){
//							 System.out.println(e);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}else if(s.getRule().getDestination()==0){//endpoint
			postRequest = new HttpPost(dev.getEndpoint());  
		}   
		
		StringEntity input;
		
		try {
			input = new StringEntity(representacionJSON);
			input.setContentType("application/json"); 
			if(postRequest!=null){
				postRequest.setEntity(input); 
				HttpResponse response = httpClient.execute(postRequest); 
			}
			
		} catch (Exception e) {
		
			e.printStackTrace();
		} 
		httpClient.getConnectionManager().shutdown();  
	
	}

}
