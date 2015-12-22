package mx.com.invex.msi.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import mx.com.invex.msi.model.Promocion;

public interface PromocionService {
	List<Integer> getMesesPromos();

	List<Promocion> getPromosByMes(String mes);

	Promocion getPromoById(long id);

	void savePromo(Promocion promo);
	
	void saveOrUpdatePromo(Promocion promo);
	void deletePromo(Promocion promo);
	List<Promocion> finbByCriteria(DetachedCriteria criteria);
}
