package mx.com.invex.msi.dao;

import java.util.List;

import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.Promocion;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("promocionDaoImpl")
public class PromocionDaoImpl implements PromocionDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<Integer> getMesesPromos(){
		Query qry =sessionFactory.getCurrentSession().createQuery("select distinct promo.plazoMeses from Promocion promo order by promo.plazoMeses");
		return qry.list();
	}

	@Override
	public List<Promocion> getPromosByMes(String mes) {
		Query qry =sessionFactory.getCurrentSession().createQuery("select promo from Promocion promo  where promo.plazoMeses = ?");
		qry.setString(0, mes);
		
		return qry.list();
	}

	@Override
	public void savePromo(Promocion promo) {
		sessionFactory.getCurrentSession().save(promo);
		
	}
	
	@Override
	public void saveOrUpdate(Promocion promo) {
		sessionFactory.getCurrentSession().saveOrUpdate(promo);
		
	}

	@Override
	public void delete(Promocion promo) {
		sessionFactory.getCurrentSession().delete(promo);
		
	}
	
	@Override
	public List<Promocion> findByCriteria(DetachedCriteria criteria) {
		Session session=sessionFactory.getCurrentSession();
		List<Promocion> results = criteria.getExecutableCriteria(session).list();
		return results;
	}
	
	@Override
	public Promocion getById(long id) {
		Promocion promo=(Promocion) sessionFactory.getCurrentSession().get(Promocion.class, new Long(id));
		return promo;
	}

}
