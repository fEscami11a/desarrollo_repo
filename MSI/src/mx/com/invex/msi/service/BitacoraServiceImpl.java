package mx.com.invex.msi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.invex.msi.dao.BitacoraDao;
import mx.com.invex.msi.model.Bitacora;
@Service("bitacoraServiceImpl")
public class BitacoraServiceImpl implements BitacoraService {

	@Autowired
	BitacoraDao bitacoraDao;
	
	@Transactional
	public void saveBitacora(Bitacora bitacora) {
		//bitacoraDao.saveBitacora(bitacora);
	}

}
