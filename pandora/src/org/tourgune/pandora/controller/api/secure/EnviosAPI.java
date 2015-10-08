package org.tourgune.pandora.controller.api.secure;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.pandora.bean.Envios;
import org.tourgune.pandora.facade.EnviosFacade;
 
 

@Controller
@RequestMapping("/api/envios")
public class EnviosAPI {
	
	@Resource
	private EnviosFacade enviosFacade;
	
	
	@RequestMapping(value = "/enviosAreasFecha", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody java.util.List<Envios> getListaEnviosAreas(
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "date_from", required = false) String date_from,
			@RequestParam(value = "date_until", required = false) String date_until) {


		java.util.List<Envios> listaEnviosAreas = null;
		try {
			listaEnviosAreas = enviosFacade.getListaEnviosAreasFecha(query, date_from,date_until);
			return listaEnviosAreas;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return listaEnviosAreas;

	}
	
	@RequestMapping(value = "/enviosUsuariosFecha", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody java.util.List<Envios> getListaEnviosUsuarios(
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "date_from", required = false) String date_from,
			@RequestParam(value = "date_until", required = false) String date_until) {

		java.util.List<Envios> listaEnviosUsuarios = null;
		try {
			listaEnviosUsuarios = enviosFacade.getListaEnviosUsuariosFecha(query, date_from,date_until);
			return listaEnviosUsuarios;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return listaEnviosUsuarios;

	}
	
}
