package mx.com.invex.msi.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import mx.com.invex.msi.model.Promocion;

public interface PromocionDao {
	public List<Integer> getMesesPromos();

	public List<Promocion> getPromosByMes(String mes);
	
	public void savePromo(Promocion promo);

	Promocion getById(long id);

	void saveOrUpdate(Promocion promo);

	void delete(Promocion promo);

	List<Promocion> findByCriteria(DetachedCriteria criteria);
}
