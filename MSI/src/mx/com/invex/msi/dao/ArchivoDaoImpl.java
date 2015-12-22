package mx.com.invex.msi.dao;

import java.util.List;

import mx.com.invex.msi.model.Archivo;
import mx.com.invex.msi.model.ArchivoDetalle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("archivoDaoImpl")
public class ArchivoDaoImpl implements ArchivoDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveArchivo(Archivo archivo) {
		sessionFactory.getCurrentSession().save(archivo);
		
	}

	@Override
	public void mergeArchivo(Archivo archivo) {
		sessionFactory.getCurrentSession().merge(archivo);
		
	}

	@Override
	public List<Archivo> findByCriteria(DetachedCriteria criteria) {
		Session session=sessionFactory.getCurrentSession();
		List<Archivo> results = criteria.getExecutableCriteria(session).list();
		return results;
	}

	@Override
	public List<ArchivoDetalle> findDetallesByCriteria(DetachedCriteria criteria) {
		Session session=sessionFactory.getCurrentSession();
		List<ArchivoDetalle> results = criteria.getExecutableCriteria(session).list();
		return results;
	}

	@Override
	public void deleteArchivo(Archivo archivo){
		sessionFactory.getCurrentSession().delete(archivo);
	}
	
}
