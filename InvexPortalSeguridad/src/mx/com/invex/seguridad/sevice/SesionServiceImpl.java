package mx.com.invex.seguridad.sevice;

import java.util.List;

import mx.com.invex.seguridad.dao.SesionDAO;
import mx.com.invex.seguridad.entidades.Sesion;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
@EnableTransactionManagement
@Service("sesionServiceImpl")
public class SesionServiceImpl implements SesionService {

	@Autowired
	SesionDAO sesionDao;
	@Transactional(readOnly=true)
	public List<Sesion> findByCriteria(DetachedCriteria criteria) {
		return sesionDao.findByCriteria(criteria);
	}

	@Transactional
	public void persist(Sesion sesion) {
		sesionDao.persist(sesion);
		
	}

	@Transactional
	public void merge(Sesion ses) {
		sesionDao.merge(ses);
		
	}

}
