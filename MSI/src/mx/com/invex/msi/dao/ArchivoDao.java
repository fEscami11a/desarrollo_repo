package mx.com.invex.msi.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import mx.com.invex.msi.model.Archivo;
import mx.com.invex.msi.model.ArchivoDetalle;

public interface ArchivoDao {
	void saveArchivo(Archivo archivo);

	void mergeArchivo(Archivo archivo);

	List<Archivo> findByCriteria(DetachedCriteria criteria);

	List<ArchivoDetalle> findDetallesByCriteria(DetachedCriteria criteria);

	void deleteArchivo(Archivo archivo); 
}
