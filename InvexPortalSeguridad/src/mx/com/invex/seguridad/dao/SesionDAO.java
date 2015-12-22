package mx.com.invex.seguridad.dao;

import java.util.List;

import mx.com.invex.seguridad.entidades.Sesion;

import org.hibernate.criterion.DetachedCriteria;

public interface SesionDAO {

	List<Sesion> findByCriteria(DetachedCriteria criteria);

	void persist(Sesion sesion);

	void merge(Sesion ses);

}
