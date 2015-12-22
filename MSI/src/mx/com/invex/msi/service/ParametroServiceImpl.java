package mx.com.invex.msi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.invex.msi.dao.ParametroDao;
import mx.com.invex.msi.model.Parametro;
@Service("parametroServiceImpl")
public class ParametroServiceImpl implements ParametroService{

	@Autowired
	ParametroDao parametroDao;
	
	
	
	
	public ParametroDao getParametroDao() {
		return parametroDao;
	}




	public void setParametroDao(ParametroDao parametroDao) {
		this.parametroDao = parametroDao;
	}




	@Transactional(readOnly=true)
	public Parametro getParamById(String clave) {
		return parametroDao.getParamById(clave);
	}

}
