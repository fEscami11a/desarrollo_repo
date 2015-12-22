package mx.com.invex.seguridad.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Humberto Enriquez
 *
 */
public interface GenericDAO <T>{


	void save(T entity);
	
	void saveOrUpdate(T entity);
	
	void update(T entity);
	
	void merge(T entity);
	
	void delete(T entity);
	
	List<T> findByExample(T entity);
	
	T findById(Class<T> entity, Serializable id);

	List<T> findByCriteria(DetachedCriteria criteria);

	
	
	




}
