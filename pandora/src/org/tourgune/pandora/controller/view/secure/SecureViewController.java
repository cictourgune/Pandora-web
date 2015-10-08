	package org.tourgune.pandora.controller.view.secure;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tourgune.pandora.bean.Developer;
import org.tourgune.pandora.facade.DeveloperFacade;
import org.tourgune.pandora.facade.FenceFacade;

@Controller
@RequestMapping("private")
public class SecureViewController {
	
	
	@Resource
	private FenceFacade fenceFacade;
	@Resource
	private DeveloperFacade developerFacade;
 
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() { 
		List listaFences =  fenceFacade.getFenceList(); 
		ModelAndView mv = new ModelAndView(); 
    	mv.addObject("listaFences", listaFences);
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	Developer developer = developerFacade.getDeveloperByUsername(username);
    	mv.addObject("developer", developer);
        mv.setViewName("dashboard");  
    	
        return mv; 
    }
	
	@RequestMapping(value = "/pageConfirmacionDialog", method = RequestMethod.GET)
    public String pageConfirmacionDialog() { 
        return "pageConfirmacionDialog";
    } 
	
	@RequestMapping(value = "/analytics", method = RequestMethod.GET)
    public ModelAndView analytics() { 
		List listaFences =  fenceFacade.getFenceList(); 
		ModelAndView mv = new ModelAndView(); 
    	mv.addObject("listaFences", listaFences);
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	Developer developer = developerFacade.getDeveloperByUsername(username);
    	mv.addObject("developer", developer);
        mv.setViewName("analytics");  
    	
        return mv; 
    }

       
}
