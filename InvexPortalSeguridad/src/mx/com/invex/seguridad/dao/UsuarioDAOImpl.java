package mx.com.invex.seguridad.dao;

import java.util.List;

import mx.com.invex.seguridad.entidades.Usuario;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("usuarioDAOImpl")
public class UsuarioDAOImpl implements UsuarioDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Usuario> findByCriteria(DetachedCriteria criteria) {
		  Session session = sessionFactory.getCurrentSession();
//	    	org.hibernate.Transaction tx = session.beginTransaction();
		List<Usuario> results = criteria.getExecutableCriteria(session).setMaxResults(1000).list();
//		  tx.commit();
		return results;
	}
	
	@Override
	public void delete(Usuario usr){
		 Session session = sessionFactory.getCurrentSession();
//	 	org.hibernate.Transaction tx = session.beginTransaction();
	 	session.delete(usr);
//	 	tx.commit();
	}
	
	@Override
	public void merge(Usuario usr){
		 Session session = sessionFactory.getCurrentSession();
		
//	 	org.hibernate.Transaction tx = session.beginTransaction();
	 	session.merge(usr);
//	 	tx.commit();
	}
}
