package mx.com.invex.msi.dao;

import java.util.List;

import mx.com.invex.msi.model.ProductoTs2;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("productoTs2DaoImpl")
public class ProductoTs2DaoImpl implements ProductoTs2Dao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
@Override
public boolean cuentaITAU(String cuenta) {
	//ProductoTs2
	if(cuenta== null){
		return false;
	}
	 Query qry= sessionFactory.getCurrentSession().createQuery("select prod.bin from ProductoTs2 prod where bin=?");
//	 if(cuenta.length() >= 6 ){
//     	for (BeanProductosTs2 prod : productos) {
// 			if(prod.getBin().equals(cuenta.substring(0,6))){
// 				return true;
// 			}
// 		}
//         
//     }
	 if(cuenta.length() >= 6 ){
		 qry.setString(0, cuenta.substring(0,6));
		 List<Object[]> list =qry.list();
			return (list!=null && !list.isEmpty());
	 }
	
	
	return false;
}
@Override
	public ProductoTs2 getProductoItau(String cuenta, String tpc, String cpc) {
	DetachedCriteria criteria = DetachedCriteria.forClass(ProductoTs2.class);
	 if(cuenta.length() >= 6 ){
		 Session session=sessionFactory.getCurrentSession();
		criteria.add(Restrictions.eq("bin", cuenta.substring(0,6)));
		criteria.add(Restrictions.eq("tpc", tpc));
		criteria.add(Restrictions.eq("cpc", cpc));
		List<ProductoTs2> lprods= criteria.getExecutableCriteria(session).list();
		if(lprods!= null && !lprods.isEmpty()){
			for (ProductoTs2 productoTs2 : lprods) {
				return productoTs2;
			}
		}
	 }
		return null;
	}

@Override
public List<ProductoTs2> getProductCatalog(){
	Query qry =sessionFactory.getCurrentSession().createQuery("select prod from ProductoTs2 prod order by prod.id desc");
	return qry.list();
}
@Override
public ProductoTs2 getById(Integer id) {
	return (ProductoTs2) sessionFactory.getCurrentSession().get(ProductoTs2.class, id);

}

}
