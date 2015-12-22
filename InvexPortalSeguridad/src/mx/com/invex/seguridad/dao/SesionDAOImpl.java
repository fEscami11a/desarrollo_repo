package mx.com.invex.seguridad.dao;

import java.util.List;

import mx.com.invex.seguridad.entidades.Sesion;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("sesionDAOImpl")
public class SesionDAOImpl implements SesionDAO{
	@Autowired
	private SessionFactory sessionFactory;
	 
   
	
	@Override
	public List<Sesion> findByCriteria(DetachedCriteria criteria) {
		  Session session = sessionFactory.getCurrentSession();
	    	
		List<Sesion> results = criteria.getExecutableCriteria(session).setMaxResults(1000).list();
		 
		return results;
	}

	@Override
	public void persist(Sesion sesion) {
		 Session session = sessionFactory.getCurrentSession();
	    	
		session.persist(sesion);
		
		 
	}

	@Override
	public void merge(Sesion ses) {
		 Session session = sessionFactory.getCurrentSession();
	    	
		session.merge(ses);
		  
	}
}
