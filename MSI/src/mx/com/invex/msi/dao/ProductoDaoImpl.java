package mx.com.invex.msi.dao;

import java.util.List;

import mx.com.invex.msi.controller.ProductoDao;
import mx.com.invex.msi.model.Producto;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("paroductoDaoImpl")
public class ProductoDaoImpl implements ProductoDao{

	
	@Autowired
	private SessionFactory sessionFactory;

	
	
	@Override
	public Producto getProductoCuenta(String cuenta, String ucode3) {
		Criteria prodCrit= sessionFactory.getCurrentSession().createCriteria(Producto.class);
		prodCrit.add(Restrictions.and(Restrictions.like( "ucode3",ucode3.charAt(0)+"%"),Restrictions.like("bin", cuenta.substring(0, 6)+"%") ));
		prodCrit.addOrder( Order.asc("id") );
		List<Producto> lprod= prodCrit.list();
		if(!lprod.isEmpty()){
			return lprod.get(0);
		}else
			return null;
	}
	
	@Override
	public List<Producto> getProductCatalog(){
		Query qry =sessionFactory.getCurrentSession().createQuery("select prod from Producto prod where prod.ucode3 is not null and prod.id not in (31,32,34) order by prod.id desc");
		return qry.list();
	}

	@Override
	public Producto getById(Integer id) {
		return (Producto) sessionFactory.getCurrentSession().get(Producto.class, id);
	}

}
