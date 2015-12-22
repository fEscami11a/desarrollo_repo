package mx.com.invex.seguridad.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import mx.com.invex.seguridad.entidades.Intentosesion;

public interface IntentoSesionDAO {

	void delete(Intentosesion intses);

	List<Intentosesion> findByCriteria(DetachedCriteria criteria);

	void merge(Intentosesion intses);

	void persist(Intentosesion intses);

}
