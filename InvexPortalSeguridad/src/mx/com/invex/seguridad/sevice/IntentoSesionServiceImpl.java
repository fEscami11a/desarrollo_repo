package mx.com.invex.seguridad.sevice;

import java.util.List;

import mx.com.invex.seguridad.dao.IntentoSesionDAO;
import mx.com.invex.seguridad.entidades.Intentosesion;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
@EnableTransactionManagement
@Service("intentoSesionServiceImpl")
public class IntentoSesionServiceImpl implements IntentoSesionService {

	@Autowired
	IntentoSesionDAO intentoSesionDAO;
	
	@Transactional
	public void delete(Intentosesion intses) {
		intentoSesionDAO.delete(intses);

	}

	@Transactional(readOnly=true)
	public List<Intentosesion> findByCriteria(DetachedCriteria criteria) {
		
		return intentoSesionDAO.findByCriteria(criteria);
	}

	@Transactional
	public void merge(Intentosesion intses) {
		intentoSesionDAO.merge(intses);

	}

	@Transactional
	public void persist(Intentosesion intses) {
		intentoSesionDAO.persist(intses);

	}

}
