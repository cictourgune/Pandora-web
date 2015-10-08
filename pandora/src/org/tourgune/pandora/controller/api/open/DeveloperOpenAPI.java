package org.tourgune.pandora.controller.api.open;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.pandora.bean.Developer;
import org.tourgune.pandora.bean.Rule;
import org.tourgune.pandora.bean.User;
import org.tourgune.pandora.drools.KBInterface;
import org.tourgune.pandora.facade.DeveloperFacade;
 
 

@Controller
@RequestMapping("/open/developer")
public class DeveloperOpenAPI {
	
	@Resource
	private DeveloperFacade developerFacade;
	
	@Resource
	private KBInterface kb;
 
	//registration
    @RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody Integer doRegistration(
				@RequestParam(value = "name", required = false) String name,
				@RequestParam(value = "password", required = false) String password,
				@RequestParam(value = "email", required = false) String email){ 
		Developer developer = new Developer();
		developer.setUsername(name);
		developer.setPassword(password);
		developer.setEmail(email); 
		
		return developerFacade.createDeveloper(developer);
		 
	}
	
	@RequestMapping(value = "/exists", method = RequestMethod.GET)
	public @ResponseBody Integer existsUsername(@RequestParam(value = "nombre", required = true) String nombre, @RequestParam(value = "email", required = true) String email){ 
		return developerFacade.existsUsername(nombre, email); 
	}
	
	//=========================================================== DEBUG API ===================================================
	
	
	@RequestMapping(value = "/userskb", method = RequestMethod.GET, produces = "application/json")
	public  @ResponseBody  List<User> getUsersInKB(){
		
		List<User> listaUsersKB  = new ArrayList<User>();
		
		QueryResults results = kb.getKsession().getQueryResults("getUsers");
		for ( QueryResultsRow row : results ) {
			Object o = row.get( "$user"); 
			try{
				if(o instanceof User){
					User u = (User)o;
					listaUsersKB.add(u);
				} 
			}catch(Exception e){  
			}   
		}  
		return listaUsersKB;
		
	}
 
		
		
	@RequestMapping(value = "/ruleskb", method = RequestMethod.GET, produces = "application/json")	
	public  @ResponseBody  List<Rule> getRulesInKB(){
		 
	   List<Rule> listaRulesKB  = new ArrayList<Rule>();
		
		QueryResults results = kb.getKsession().getQueryResults("getCurrentRules");
		for ( QueryResultsRow row : results ) {
			Object o = row.get( "$rule"); 
			try{
				Rule r = (Rule)o;
				listaRulesKB.add(r);
			}catch(Exception e){  
			}   
		}
		
		return listaRulesKB;
		
	}
 
}
