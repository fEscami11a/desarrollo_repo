package mx.com.invex.seguridad.sevice;

import java.util.List;

import mx.com.invex.seguridad.entidades.Sesion;

import org.hibernate.criterion.DetachedCriteria;

public interface SesionService {

	List<Sesion> findByCriteria(DetachedCriteria criteria);

	void persist(Sesion sesion);

	void merge(Sesion ses);

}
