package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.dao.ArchivoDetalleDao;
import mx.com.invex.msi.model.ArchivoDetalle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("archivoDetalleServiceImpl")
public class ArchivoDetalleServiceImpl implements ArchivoDetalleService{

	@Autowired
	ArchivoDetalleDao archivoDetalleDao;
	
	
	
	public ArchivoDetalleDao getArchivoDetalleDao() {
		return archivoDetalleDao;
	}



	public void setArchivoDetalleDao(ArchivoDetalleDao archivoDetalleDao) {
		this.archivoDetalleDao = archivoDetalleDao;
	}



	@Transactional(readOnly=true)
	public List<ArchivoDetalle> getArDetalleByCuenta(String cuenta) {
		return archivoDetalleDao.getArDetalleByCuenta(cuenta);
	}

}
