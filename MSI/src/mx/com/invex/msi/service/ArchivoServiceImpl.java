package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.dao.ArchivoDao;
import mx.com.invex.msi.model.Archivo;
import mx.com.invex.msi.model.ArchivoDetalle;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("archivoServiceImpl")
public class ArchivoServiceImpl implements ArchivoService{

	@Autowired
	ArchivoDao archivoDao;
	
	@Transactional
	public void saveArchivo(Archivo archivo) {
		archivoDao.saveArchivo(archivo);
	}

	@Transactional
	public void mergeArchivo(Archivo archivo) {
		archivoDao.mergeArchivo(archivo);
		
	}

	@Transactional(readOnly=true)
	public List<Archivo> findByCriteria(DetachedCriteria criteria) {
		return archivoDao.findByCriteria(criteria);
	}

	@Transactional(readOnly=true)
	public List<ArchivoDetalle> findDetallesByCriteria(DetachedCriteria criteria) {
		return archivoDao.findDetallesByCriteria(criteria);
	}
	
	@Transactional
	public void deleteArchivo(Archivo archivo){
		 archivoDao.deleteArchivo(archivo);
	}

}
