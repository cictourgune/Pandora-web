package org.tourgune.pandora.controller.api.secure;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.pandora.bean.Developer;
import org.tourgune.pandora.facade.DeveloperFacade;
 
 

@Controller
@RequestMapping("/api/developer")
public class DeveloperAPI {
	@Resource
	private DeveloperFacade developerFacade;
	
	//update developer
		@RequestMapping(method = RequestMethod.PUT)
		public @ResponseBody Integer updateDeveloper(@RequestBody Developer developer) { 
			return developerFacade.updateDeveloper(developer);
		}
		@RequestMapping(value = "/cert", method = RequestMethod.PUT)
		public @ResponseBody Integer updateDeveloperCert(@RequestBody Developer developer) { 
			System.out.println("/cert");
			return developerFacade.updateDeveloperCert(developer);
		}
}
