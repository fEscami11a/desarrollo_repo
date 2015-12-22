package mx.com.invex.msi.dao;

import java.util.Date;
import java.util.List;

import mx.com.invex.msi.model.ArchivoDetalle;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("archivoDetalleDaoImpl")
public class ArchivoDetalleDaoImpl implements ArchivoDetalleDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	 public SessionFactory getSessionFactory() {
		  return sessionFactory;
		 }
	
	 public void setSessionFactory(SessionFactory sessionFactory) {
		  this.sessionFactory = sessionFactory;
		 }
	

	@Override
	public List<ArchivoDetalle> getArDetalleByCuenta(String cuenta) {
				 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ArchivoDetalle.class);
				 criteria.createAlias("campania","camp", JoinType.INNER_JOIN);
				 criteria.addOrder( Order.desc("idDetalle") );
        criteria.add(Restrictions.eq("cuenta", cuenta));
        criteria.add(Restrictions.ge("camp.fechaMaximaRegistro", new Date()));
        return criteria.list();

	}

}
