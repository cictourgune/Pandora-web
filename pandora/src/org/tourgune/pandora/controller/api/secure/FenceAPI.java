package org.tourgune.pandora.controller.api.secure;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.pandora.bean.Developer;
import org.tourgune.pandora.bean.Fence;
import org.tourgune.pandora.facade.DeveloperFacade;
import org.tourgune.pandora.facade.FenceFacade;
 
 

@Controller
@RequestMapping("/api/fence")
public class FenceAPI {
	
	@Resource
	private FenceFacade fenceFacade;
	
	@Resource
	private DeveloperFacade developerFacade;
	
	//insert fence (y regla asociada)
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Integer insertFence(@RequestBody Fence fence) { 
		 
		//Comprobar que no hay dos fences con mismo nombre!!
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Developer developer = developerFacade.getDeveloperByUsername(username);
		if(!fenceFacade.isUniqueFenceName(-1, fence.getName(), developer.getId())){
			return -1;
		}else{ 
			fence.setDeveloper(developer);
			return fenceFacade.insertFence(fence);
		} 
	}
	
	//update fence (y regla asociada)
		@RequestMapping(method = RequestMethod.PUT)
		public @ResponseBody Integer updateFence(@RequestBody Fence fence) { 
			//Comprobar que no hay dos fences con mismo nombre!!
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Developer developer = developerFacade.getDeveloperByUsername(username);
			if(!fenceFacade.isUniqueFenceName(fence.getId(), fence.getName(), developer.getId())){
				return -1;
			}else{
				fence.setDeveloper(developer);
				return fenceFacade.updateFence(fence);
			} 
		}
	
	//read fence
	@RequestMapping(value = "/{fenceid}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Fence getFence(@PathVariable(value = "fenceid") Integer fenceid) {  
		Fence fence = fenceFacade.getFence(fenceid);
	 	return fence;
	}
	//delete fence
	@RequestMapping(value = "/{fenceid}", method = RequestMethod.DELETE)
	public @ResponseBody Integer deletePage(@PathVariable(value = "fenceid") Integer fenceid) { 
		if(fenceFacade.deleteFence(fenceid)){
			return 1;
		}
		return -1;
	}
	@RequestMapping(value = "/fences", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List getAllFences() {  
		return fenceFacade.getFenceList();
	}
	
	
}
