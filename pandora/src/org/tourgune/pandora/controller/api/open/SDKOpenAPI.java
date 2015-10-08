package org.tourgune.pandora.controller.api.open;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.pandora.bean.Developer;
import org.tourgune.pandora.bean.Fence;
import org.tourgune.pandora.bean.User;
import org.tourgune.pandora.drools.KBInterface;
import org.tourgune.pandora.facade.DeveloperFacade;
import org.tourgune.pandora.facade.FenceFacade;
import org.tourgune.pandora.facade.UserFacade;


@Controller
@RequestMapping("/open/sdk")
public class SDKOpenAPI {
	
	@Resource
	private FenceFacade fenceFacade; 
	
	@Resource
	private DeveloperFacade developerFacade; 
	
	@Resource
	private UserFacade userFacade; 
	
	@Resource
	private KBInterface kb;
	
//	//----------------------------------------------------------TEST ENDPOINT---------------------------------------------------------
//	
//	@RequestMapping(value = "/message", method = RequestMethod.POST, consumes = "application/json")
//	public void processMessage(@RequestBody  String messageJSON) {  
//		System.out.println("New message \n"+messageJSON);
//		
//		 
//		
////		JSONObject json = (JSONObject) JSONSerializer.toJSON( messageJSON );  
////		//POST TO GCM SERVICE
////		String gcm_id = (String) json.get("gcm_id");
////		
////		String url = (String) json.get("url");
////		
////		 
////		HttpPost postRequest = new HttpPost("http://"+Configuration.getInstance().getProperty(Configuration.GCM_DOMAIN)+"/gcm/api/v1/device/"+gcm_id+"/message");
////	 
////		StringEntity se = null;
////		try {
////			se = new StringEntity(url);// 
////		} catch (UnsupportedEncodingException e1) { 
////			e1.printStackTrace();
////		}
////		//se.setContentType("application/json");
////		postRequest.setEntity(se);
////		
////		BasicHttpResponse httpResponse = null; 
////		String respStr="";
////		try { 
////			HttpClient httpclient = new DefaultHttpClient(); 
////			httpResponse = (BasicHttpResponse) httpclient.execute(postRequest); 
////			respStr = EntityUtils.toString(httpResponse.getEntity());
////			
////		} catch (Exception e) { 
////			 e.printStackTrace();
////		} 
//		
//		
//		
//		
//	} 
//	
	
	
	//---------------------------------------------------------USERS-----------------------------------------------------------------
	//
	//Procesamiento del envío de datos desde dispositivo móvil 
	 
		/** el gcm_id y los extras son opcionales
		 * 
		   {
				"id": "idusuario",
				"latitude": 45.34,
				"longitude": -1.7,
				"gcm_id": "235jasfkdf",             
                "extras" : { 
                          "test": "si"
                  }  
			}
		 * 		
		 */
		@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
		public @ResponseBody Integer processUserOnTheMove(@RequestBody User user, @RequestParam(value = "key", required = true) String key) {
			
			//comprobar que devToken existe
			if(developerFacade.existsDeveloperKey(key)){ 
				user.setDev_key(key);
				//insertar en DB si no estaba
				userFacade.insertUser(user);
				//insertar en KB
				kb.saveOrUpdateUser(user);  
				return 1;
			}else{
				return -1;
			}
		} 
		
		
		@RequestMapping(value = "/user", method = RequestMethod.DELETE)
		public @ResponseBody Integer deleteUser(@RequestParam(value = "userid", required = true)  String userId, @RequestParam(value = "key", required = true) String key) { 
		 
			//comprobar que devToken existe
			if(developerFacade.existsDeveloperKey(key)){ 
				User user = new User();
				user.setId(userId);
				user.setDev_key(key);
				//borrar de KB
				kb.deleteObject(user); 
				return 1;
			}else{
				return -1;
			}
			 
		} 
		
		
		@RequestMapping(value = "/user/ibeacon", method = RequestMethod.POST, consumes = "application/json")
		public @ResponseBody Integer processUserOnTheMoveIBeacon(@RequestBody User user, @RequestParam(value = "key", required = true) String key) { 
		  
			//comprobar que devToken existe
			if(developerFacade.existsDeveloperKey(key)){ 
				user.setDev_key(key);
				//insertar en DB si no estaba
				userFacade.insertUser(user);
				//insertar en KB
				kb.saveOrUpdateUserIBeacon(user);  
				return 1;
			}else{
				return -1;
			}
		} 
	
	//---------------------------------------------------------FENCES-----------------------------------------------------------------
	
	
	//create fence
	/**
	 *
	 	{
			"name": "myfence",         //requerido
			"ibeacon": "4789-46-4564156146",         //opcional, si se pone, es una fence interior
			"centreLatitude": 45.34,   //requerido
			"centreLongitude": -1.7,  //requerido
			"radius" : 102.5,			//requerido en metros
			"rule": {
				"status_fence": 1,    //1 entra, 0 sale
				"period_fence": 1,    //1 primera vez, 0 siempre
				"fromDate" : "2009-01-01",   //opcional, pero si lo pones, el toDate es obligatorio
				"toDate" : "2009-01-02",     //opcional, pero si lo pones, el fromDate es obligatorio
				"fromHour" : "12",           //opcional, si no pones se establece por defecto a 0
				"toHour" : "20",			 //opcional, si no pones se establece por defecto a 24
				"destination" : 1,     //1 movil, 0 endpoint
				"data": "my data"		//requerido
			} 
		}
	 * 
	 */
	@RequestMapping(value = "/fences", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<String> insertFence(@RequestParam(value = "key", required = true) String key, @RequestBody Fence fence) {
		//comprobar que devToken existe
		if(developerFacade.existsDeveloperKey(key)){
			//comprobar que no hay otra fence para ese developer con el mismo nombre
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Developer developer = developerFacade.getDeveloperByUsername(username); 
			if(!fenceFacade.isUniqueFenceName(-1, fence.getName(), developer.getId())){
				return new ResponseEntity<String>("The name of the fence already exists", HttpStatus.BAD_REQUEST);
			} 
			
			//-----validar datos de la fence
			
			//lat y long existentes
			if(fence.getCentreLatitude()==null||fence.getCentreLongitude()==null){
				return new ResponseEntity<String>("Latitude and Longitude are required", HttpStatus.BAD_REQUEST);
			}
			if(fence.getRadius()==null){
				return new ResponseEntity<String>("Radius is required", HttpStatus.BAD_REQUEST);
			}else if(fence.getRadius()<100){
				return new ResponseEntity<String>("Radius has to be higher than 100m.", HttpStatus.BAD_REQUEST);
			}
			//pasar radio a km para guardar en DB
			fence.setRadius(fence.getRadius()/1000);
			
			//validar datos de la regla
			if(fence.getRule().getStatus_fence()==null){
				return new ResponseEntity<String>("Status fence required. 1-arrives, 0-leaves", HttpStatus.BAD_REQUEST);
			}else if(fence.getRule().getStatus_fence()<0 || fence.getRule().getStatus_fence()>1){
				return new ResponseEntity<String>("Status fence required. 1-arrives, 0-leaves", HttpStatus.BAD_REQUEST);
			}
			
			if(fence.getRule().getPeriod_fence()==null){
				return new ResponseEntity<String>("Period fence required. 1-arrives, 0-leaves", HttpStatus.BAD_REQUEST);
			}else if(fence.getRule().getPeriod_fence()<0 || fence.getRule().getPeriod_fence()>1){
				return new ResponseEntity<String>("Period fence required. 1-first time, 0-always", HttpStatus.BAD_REQUEST);
			}
		
			//validar que from sea menor que to 
			if(fence.getRule().getToDate()!=null && fence.getRule().getFromDate()!=null && !fence.getRule().getToDate().trim().isEmpty() && !fence.getRule().getFromDate().trim().isEmpty()){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
				try { 
					Date fromdate = formatter.parse(fence.getRule().getFromDate());
					Date todate = formatter.parse(fence.getRule().getToDate());
					if(fromdate.after(todate)){
						return new ResponseEntity<String>("Dates are incorrect: fromDate has to be before toDate", HttpStatus.BAD_REQUEST);
					}			 
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<String>("Exception in date format", HttpStatus.BAD_REQUEST);
				}
			}
			
			//se tienen que especificar las dos fechas si una de las dos se especifica
			if(fence.getRule().getToDate()!=null 
					&& !fence.getRule().getToDate().trim().isEmpty() 
						&&	(fence.getRule().getFromDate()==null || fence.getRule().getFromDate().trim().isEmpty())){
				return new ResponseEntity<String>("From date must be defined", HttpStatus.BAD_REQUEST);
			}
			
			if(fence.getRule().getFromDate()!=null 
					&& !fence.getRule().getFromDate().trim().isEmpty() 
						&&	(fence.getRule().getToDate()==null || fence.getRule().getToDate().trim().isEmpty())){
				return new ResponseEntity<String>("To date must be defined", HttpStatus.BAD_REQUEST);
			}
			
			
			//horas
			if(fence.getRule().getFromHour()<0 || fence.getRule().getFromHour()>23){
				return new ResponseEntity<String>("From hour out of range", HttpStatus.BAD_REQUEST);
			}
			
			if(fence.getRule().getToHour()<1 || fence.getRule().getToHour()>24){
				return new ResponseEntity<String>("To hour out of range", HttpStatus.BAD_REQUEST);
			}
			
			if(fence.getRule().getFromHour()>fence.getRule().getToHour()){
				return new ResponseEntity<String>("Hour ranges not valid", HttpStatus.BAD_REQUEST);
			}
			
			
			//validar destination
			if(fence.getRule().getDestination()==null){
				return new ResponseEntity<String>("Destination required. 1-mobile, 0-endpoint", HttpStatus.BAD_REQUEST);
			}else if(fence.getRule().getDestination()<0 || fence.getRule().getDestination()>1){
				return new ResponseEntity<String>("Destination required. 1-mobile, 0-endpoint", HttpStatus.BAD_REQUEST);
			}
			
			
			//si destination es 0, comprobar que haya endpoint configurado		
			if(fence.getRule().getDestination()==0 && developer.getEndpoint()==null){ 
				return new ResponseEntity<String>("Endpoint configuration required.", HttpStatus.BAD_REQUEST);
			}
			
			//comprobar tamaño de los datos que no sea superior a 4k bytes
			if(fence.getRule().getData()!=null){
				String s = fence.getRule().getData();
				byte[] data;
				try {
					data = s.getBytes("UTF-8");
					if(data.length>4000){
						return new ResponseEntity<String>("Too much data to send!", HttpStatus.BAD_REQUEST);
					} 
				} catch (UnsupportedEncodingException e) { 
					e.printStackTrace();
				}
			}else{
				return new ResponseEntity<String>("Data required.", HttpStatus.BAD_REQUEST);
			}
			
			
			fence.setDeveloper(developer);
			if(fenceFacade.insertFence(fence)==1){
				return new ResponseEntity<String>(HttpStatus.OK);
			}
			
		}else{
			return new ResponseEntity<String>("Invalid developer token", HttpStatus.BAD_REQUEST);
		}
		return null; 
	}	
	
	//update fence
	@RequestMapping(value = "/fences", method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody ResponseEntity<String> updateFence(@RequestParam(value = "key", required = true) String key, @RequestBody Fence fence) { 
		//Comprobar que no hay dos fences con mismo nombre!!
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Developer developer = developerFacade.getDeveloperByUsername(username);
		
		//comprobar que devToken existe
		if(developerFacade.existsDeveloperKey(key)){
			
			
			Fence fenceDB = fenceFacade.getFence(fence.getId());
			//ver si existe la fence
			if(fenceDB==null){
				return new ResponseEntity<String>("The fence does not exist", HttpStatus.NOT_FOUND);
			}
			
			if(!fenceDB.getName().trim().equals(fence.getName().trim())){ //si se va a cambiar el nombre, validar que no haya otra con igual nombre
				System.out.println("Ha entrado! "+fenceDB.getName().trim()+", "+fence.getName().trim());
				if(!fenceFacade.isUniqueFenceName(-1, fence.getName(), developer.getId())){
					return new ResponseEntity<String>("The name of the fence already exists", HttpStatus.BAD_REQUEST);
				} 
			}
			 
			//-----validar datos de la fence
			
			//lat y long existentes
			if(fence.getCentreLatitude()==null || fence.getCentreLongitude()==null){
				return new ResponseEntity<String>("Latitude and Longitude are required", HttpStatus.BAD_REQUEST);
			}
			if(fence.getRadius()==null){
				return new ResponseEntity<String>("Radius is required", HttpStatus.BAD_REQUEST);
			}else if(fence.getRadius()<100){
				return new ResponseEntity<String>("Radius has to be higher than 100m.", HttpStatus.BAD_REQUEST);
			}
			//pasar radio a km para guardar en DB
			fence.setRadius(fence.getRadius()/1000);
			
			//validar datos de la regla
			if(fence.getRule().getStatus_fence()==null){
				return new ResponseEntity<String>("Status fence required. 1-arrives, 0-leaves", HttpStatus.BAD_REQUEST);
			}else if(fence.getRule().getStatus_fence()<0 || fence.getRule().getStatus_fence()>1){
				return new ResponseEntity<String>("Status fence required. 1-arrives, 0-leaves", HttpStatus.BAD_REQUEST);
			}
			
			if(fence.getRule().getPeriod_fence()==null){
				return new ResponseEntity<String>("Period fence required. 1-arrives, 0-leaves", HttpStatus.BAD_REQUEST);
			}else if(fence.getRule().getPeriod_fence()<0 || fence.getRule().getPeriod_fence()>1){
				return new ResponseEntity<String>("Period fence required. 1-first time, 0-always", HttpStatus.BAD_REQUEST);
			}
		
			//validar que from sea menor que to 
			if(fence.getRule().getToDate()!=null && fence.getRule().getFromDate()!=null && !fence.getRule().getToDate().trim().isEmpty() && !fence.getRule().getFromDate().trim().isEmpty()){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
				try { 
					Date fromdate = formatter.parse(fence.getRule().getFromDate());
					Date todate = formatter.parse(fence.getRule().getToDate());
					if(fromdate.after(todate)){
						return new ResponseEntity<String>("Dates are incorrect: fromDate has to be before toDate", HttpStatus.BAD_REQUEST);
					}			 
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<String>("Exception in date format", HttpStatus.BAD_REQUEST);
				}
			}
			
			//se tienen que especificar las dos fechas si una de las dos se especifica
			if(fence.getRule().getToDate()!=null 
					&& !fence.getRule().getToDate().trim().isEmpty() 
						&&	(fence.getRule().getFromDate()==null || fence.getRule().getFromDate().trim().isEmpty())){
				return new ResponseEntity<String>("From date must be defined", HttpStatus.BAD_REQUEST);
			}
			
			if(fence.getRule().getFromDate()!=null 
					&& !fence.getRule().getFromDate().trim().isEmpty() 
						&&	(fence.getRule().getToDate()==null || fence.getRule().getToDate().trim().isEmpty())){
				return new ResponseEntity<String>("To date must be defined", HttpStatus.BAD_REQUEST);
			}
			
			
			//horas
			if(fence.getRule().getFromHour()<0 || fence.getRule().getFromHour()>23){
				return new ResponseEntity<String>("From hour out of range", HttpStatus.BAD_REQUEST);
			}
			
			if(fence.getRule().getToHour()<1 || fence.getRule().getToHour()>24){
				return new ResponseEntity<String>("To hour out of range", HttpStatus.BAD_REQUEST);
			}
			
			if(fence.getRule().getFromHour()>fence.getRule().getToHour()){
				return new ResponseEntity<String>("Hour ranges not valid", HttpStatus.BAD_REQUEST);
			}
			
			
			//validar destination
			if(fence.getRule().getDestination()==null){
				return new ResponseEntity<String>("Destination required. 1-mobile, 0-endpoint", HttpStatus.BAD_REQUEST);
			}else if(fence.getRule().getDestination()<0 || fence.getRule().getDestination()>1){
				return new ResponseEntity<String>("Destination required. 1-mobile, 0-endpoint", HttpStatus.BAD_REQUEST);
			}
			
			
			//si destination es 0, comprobar que haya endpoint configurado		
			if(fence.getRule().getDestination()==0 && developer.getEndpoint()==null){ 
				return new ResponseEntity<String>("Endpoint configuration required.", HttpStatus.BAD_REQUEST);
			}
			
			//comprobar tamaño de los datos que no sea superior a 4k bytes
			if(fence.getRule().getData()!=null){
				String s = fence.getRule().getData();
				byte[] data;
				try {
					data = s.getBytes("UTF-8");
					if(data.length>4000){
						return new ResponseEntity<String>("Too much data to send!", HttpStatus.BAD_REQUEST);
					} 
				} catch (UnsupportedEncodingException e) { 
					e.printStackTrace();
				}
			}else{
				return new ResponseEntity<String>("Data required.", HttpStatus.BAD_REQUEST);
			}
			
			
			fence.setDeveloper(developer);
			if(fenceFacade.updateFence(fence)==1){
				return new ResponseEntity<String>(HttpStatus.OK);
			}		
			
			
		}else{
			return new ResponseEntity<String>("Invalid developer token", HttpStatus.BAD_REQUEST);
		} 
		
	
		 return null;
		  
	}
	
	//delete fence 
	@RequestMapping(value = "/fences/{fenceid}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> deleteFence(@RequestParam(value = "key", required = true) String key, @PathVariable(value = "fenceid") Integer fenceid) {
		if(fenceFacade.deleteFence(fenceid)){
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>("Fence not found", HttpStatus.NOT_FOUND);
	}
	
	//get all fences   
	@RequestMapping(value = "/fences", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List getAllFences(@RequestParam(value = "key", required = true) String key) { 
		
		List fenceList = fenceFacade.getFenceList();
		for(int i=0;i<fenceList.size();i++){
			Fence fence = (Fence) fenceList.get(i);
			fence.setRadius(fence.getRadius()*1000);  //En DB el radio está en km!!!
		} 
		return fenceList;
	}
	
	//get a particular fence 
	@RequestMapping(value = "/fences/{fenceid}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Fence getFence(@RequestParam(value = "key", required = true) String key, @PathVariable(value = "fenceid") Integer fenceid) { 
		Fence fence = fenceFacade.getFence(fenceid);
		if(fence==null){
			return null;
		}
		fence.setRadius(fence.getRadius()*1000);  //En DB el radio está en km!!!
		return fence;
	}
		 
}
