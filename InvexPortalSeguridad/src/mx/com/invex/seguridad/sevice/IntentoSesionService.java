package mx.com.invex.seguridad.sevice;

import java.util.List;

import mx.com.invex.seguridad.entidades.Intentosesion;

import org.hibernate.criterion.DetachedCriteria;

public interface IntentoSesionService {
	void delete(Intentosesion intses);

	List<Intentosesion> findByCriteria(DetachedCriteria criteria);

	void merge(Intentosesion intses);

	void persist(Intentosesion intses);

}
