package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.dao.PromocionDao;
import mx.com.invex.msi.model.Promocion;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("promocionServiceImpl")
public class PromocionServiceImpl implements PromocionService{

@Autowired
PromocionDao promocionDao;
@Transactional(readOnly=true)
public List<Integer> getMesesPromos(){
return promocionDao.getMesesPromos();	
}
@Transactional(readOnly=true)
public List<Promocion> getPromosByMes(String mes) {
	return promocionDao.getPromosByMes(mes);
}

@Transactional(readOnly=true)
public Promocion getPromoById(long id) {
	return promocionDao.getById(id);
}

@Transactional(readOnly=true)
public List<Promocion> finbByCriteria(DetachedCriteria criteria){
	return promocionDao.findByCriteria(criteria);
}

@Transactional
public void savePromo(Promocion promo) {
	promocionDao.savePromo(promo);
}

@Transactional
public void saveOrUpdatePromo(Promocion promo) {
	promocionDao.saveOrUpdate(promo);
}

@Transactional
public void deletePromo(Promocion promo) {
	promocionDao.delete(promo);
}



}
