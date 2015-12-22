package mx.com.invex.seguridad.dao;

import java.util.List;

import mx.com.invex.seguridad.entidades.Intentosesion;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("intentoSesionDAOImpl")
public class IntentoSesionDAOImpl implements IntentoSesionDAO {
	@Autowired
	private SessionFactory sessionFactory;

@Override
public void delete(Intentosesion intses){
	 Session session = sessionFactory.getCurrentSession();
 
 	session.delete(intses);
 
}
@Override
public List<Intentosesion> findByCriteria(DetachedCriteria criteria) {
	  Session session = sessionFactory.getCurrentSession();
  
	List<Intentosesion> results = criteria.getExecutableCriteria(session).setMaxResults(1000).list();
	
	return results;
}

@Override
public void merge(Intentosesion intses){
	 Session session = sessionFactory.getCurrentSession();
 	
 	session.merge(intses);
 	
}

@Override
public void persist(Intentosesion intses){
	 Session session = sessionFactory.getCurrentSession();
 	
 	session.persist(intses);
 	
}
}
