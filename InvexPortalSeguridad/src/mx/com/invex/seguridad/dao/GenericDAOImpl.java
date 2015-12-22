/**
 * 
 */
package mx.com.invex.seguridad.dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Humberto Enriquez
 *
 */
@Repository("genericDAOImpl")
public class GenericDAOImpl <T>  implements GenericDAO <T>  {


	private static final Logger log = Logger.getLogger( GenericDAOImpl.class.getName() );
	@Autowired
	private SessionFactory sessionFactory;
	private Session session;
	public void setSessionFactory(
			SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(T entity) {

		getCurrentSession();
		
		try {
			session.save(entity);
			
		}catch(RuntimeException re){
			try{
				
			}catch(RuntimeException rex){
				log.log(Level.SEVERE, rex.toString(), rex );
			}
		}


	}

	@Override
	public void update(T entity) {
		getCurrentSession();
	
		try {
			session.update(entity);
		
		}catch(RuntimeException re){
			try{
				
			}catch(RuntimeException rex){
				log.log(Level.SEVERE, rex.toString(), rex );
			}
		}


	}

	@Override
	public List<T> findByExample(T entity) {
		getCurrentSession();
		List<T> resultSet = session.createCriteria(entity.getClass()).
				add(Example.create(entity)).list();
		return resultSet;
	}

	@Override
	public T findById(Class<T> entity, Serializable id) {
		getCurrentSession();
		session = sessionFactory.openSession();
		T result = (T) session.load(entity, id);
	
		return result;
	}

	@Override
	public void saveOrUpdate(T entity) {
		getCurrentSession();
		
		try {
			session.saveOrUpdate(entity);
			
		}catch(RuntimeException re){
			try{
			
			}catch(RuntimeException rex){
				log.log(Level.SEVERE, rex.toString(), rex );
			}
		}


	}

	@Override
	public void merge(T entity) {
		getCurrentSession();
	
		try {
			session.merge(entity);
			
		}catch(RuntimeException re){
			try{
				
			}catch(RuntimeException rex){
				log.log(Level.SEVERE, rex.toString(), rex );
			}
		}



	}

	@Override
	public void delete(T entity) {
		getCurrentSession();
		
		try {
			session.delete(entity);
			
		}catch(RuntimeException re){
			try{
				
			}catch(RuntimeException rex){
				log.log(Level.SEVERE, rex.toString(), rex );
			}
		
		}

	}
	

	
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		getCurrentSession();

		List<T> results = criteria.getExecutableCriteria(session).setMaxResults(1000).list();
//		for (T t : results) {
//			session.refresh(t);
//		}
		
		 
		return results;
	}

	private void getCurrentSession(){
		
		if(session == null){
			this.session = sessionFactory.openSession();
		}
	}




}
