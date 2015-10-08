package org.tourgune.pandora.facade;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.pandora.bean.Envios;
import org.tourgune.pandora.dao.EnviosDao;

@Service("analyticsFacade")
@Transactional(propagation = Propagation.REQUIRED)
public class EnviosFacade {

	@Resource
	private EnviosDao enviosDao;

	public List<Envios> getListaEnviosAreasFecha(String query, String date_from,
			String date_until) {
		
		return enviosDao.getListaEnviosAreasFecha(query, date_from, date_until);
	}
	
	public List<Envios> getListaEnviosUsuariosFecha(String query, String date_from,
			String date_until) {
		
		return enviosDao.getListaEnviosUsuariosFecha(query, date_from, date_until);
	}
	
	
}
