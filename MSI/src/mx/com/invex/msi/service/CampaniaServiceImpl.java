package mx.com.invex.msi.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.invex.msi.dao.CampaniaDao;
import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.Producto;
import mx.com.invex.msi.model.ProductoTs2;
@Service("campaniaServiceImpl")
public class CampaniaServiceImpl implements CampaniaService{

	
	@Autowired
	CampaniaDao campaniaDao;
	
	
	public final CampaniaDao getCampaniaDao() {
		return campaniaDao;
	}

	public final void setCampaniaDao(CampaniaDao campaniaDao) {
		this.campaniaDao = campaniaDao;
	}

	@Transactional(readOnly=true)
	public List<Campania> getCampaniasMasivas() {
		return campaniaDao.getCampaniasMasivas();
	}

	@Transactional(readOnly=true)
	public Campania getCampaniaInternal() {
		return campaniaDao.getCampaniaInternal();
	}

	@Transactional(readOnly=true)
	public List<Campania> getCampaniasComercios() {
		return campaniaDao.getCampaniasComercios();
	}

	@Transactional(readOnly=true)
	public Campania getCampNalProg0() {
		return campaniaDao.getCampaniaNalProg0();
	}

	@Transactional(readOnly=true)
	public Campania getCampaniaById(Long id) {
		return campaniaDao.getCampaniaById(id);
	}

	@Transactional(readOnly=true)
	public List<Campania> getCampaniaByParams(Date fechaIn, Date fechaMaxReg,
			String tipoCampania, Producto producto) {
		return campaniaDao.getCampaniaByParams(fechaIn, fechaMaxReg,tipoCampania, producto);
		
	}

	@Transactional(readOnly=true)
	public List<Campania> getCampaniaByTipo(String tipoCamp) {
		return campaniaDao.getCampaniaByTipo(tipoCamp);
	}

	@Transactional(readOnly=true)
	public List<Object[]> getCampComboByTipo(String tipoCamp){
		return campaniaDao.getCampComboByTipo(tipoCamp);
	}
	
	@Transactional
	public void saveOrUpdate(Campania campania) {
		campaniaDao.saveOrUpdate(campania);
	}

	@Transactional
	public void updatePromosCamp(Campania campania) {
		campaniaDao.updatePromosCamp(campania);
		
	}

	@Transactional(readOnly=true)
	public List<Campania> findByCriteria(DetachedCriteria criteria) {
		return campaniaDao.findByCriteria(criteria);
	}

	@Transactional(readOnly=true)
	public List<Campania> getCampaniaProdTs2(Date fechaIn, Date fechaMaxReg,
			String tipoCampania, ProductoTs2 producto){
		return campaniaDao.getCampaniaProdTs2(fechaIn, fechaMaxReg,
				 tipoCampania,  producto);
	}

}
