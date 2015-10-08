package org.tourgune.pandora.controller.view.open;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tourgune.pandora.facade.DeveloperFacade;
import org.tourgune.pandora.facade.FenceFacade;

@Controller
public class OpenViewController {
 
	@Resource
	private FenceFacade fenceFacade;
	@Resource
	private DeveloperFacade developerFacade;
 
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    } 
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index_2() {
        return "index"; 
    } 
    
    @RequestMapping(value = "/privacidad", method = RequestMethod.GET)
    public String privacidad() {
        return "privacidad"; 
    } 
    
    @RequestMapping(value = "/informacion", method = RequestMethod.GET)
    public String informacion() {
        return "informacion"; 
    } 
    
    @RequestMapping(value = "/contacto", method = RequestMethod.GET)
    public String contacto() {
        return "contacto"; 
    } 
    
    @RequestMapping(value = "/registro", method = RequestMethod.GET)
    public String registro() { 
        return "registro";
    } 
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() { 
        return "login";
    }  
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() { 
        return "logout";
    }  
    
    @RequestMapping(value = "/validation", method = RequestMethod.GET)
    public String validation() { 
        return "validation";
    }  
    
    @RequestMapping(value = "/developers", method = RequestMethod.GET)
    public String download() { 
        return "developers";
    }  
    
	@RequestMapping(value = "/validationKey", method = RequestMethod.GET)
	public ModelAndView validateUser(@RequestParam(value = "key", required = true) String key){ 
	
		ModelAndView mv = new ModelAndView(); 
		//si existe el token, acceder a dashboard
		if(developerFacade.validateUser(key)==1){
			 mv.setViewName("login"); 
		}else{
			 mv.setViewName("validation"); 
		} 
        return mv;
        
	} 
    
       
}
