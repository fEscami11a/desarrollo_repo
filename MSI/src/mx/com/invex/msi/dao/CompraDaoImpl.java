package mx.com.invex.msi.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.util.MSIHelper;
@Repository("compraDaoImpl")
public class CompraDaoImpl implements CompraDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	 public SessionFactory getSessionFactory() {
		  return sessionFactory;
		 }
	
	 public void setSessionFactory(SessionFactory sessionFactory) {
		  this.sessionFactory = sessionFactory;
		 }
	
	@Override
	public String getStatusCompra(Compra compra) {
		Query query=null;
		
			 query =sessionFactory.getCurrentSession().createQuery(
					"Select c.idEdoPromocion from Compra c where c.cuentaFacturadora = ? and c.codigoTransaccion = ? and c.numRefTran = ?");
			
			query.setString(0, compra.getCuentaFacturadora());
			query.setInteger(1, compra.getCodigoTransaccion());
	//		query.setDouble(2, compra.getMonto());
			query.setString(2, compra.getNumRefTran());
		
		List<Compra> lcompras= query.list();
		if(query.list().isEmpty()){
			return null;
		}else{
			return (String) query.list().get(0);
		}
	}
	
	
	@Override
	public String getStatusCompraItau(Compra compra) {
		Query query=null;
		
	
			query =sessionFactory.getCurrentSession().createQuery(
					"Select c.idEdoPromocion from Compra c where c.cuenta = ? and c.codigoTransaccion = ? and c.numRefTran = ?");
			query.setString(0, compra.getCuenta());
			
			query.setInteger(1, compra.getCodigoTransaccion());
			
//			query.setDouble(2, compra.getMonto());
			query.setString(2, compra.getNumRefTran());
		
		List<Compra> lcompras= query.list();
		if(query.list().isEmpty()){
			return null;
		}else{
			return (String) query.list().get(0);
		}
	}
	
	

	
	@Override
	public Integer getFolio(){
		SQLQuery query=sessionFactory.getCurrentSession().createSQLQuery("select folio.nextval folio from dual");
		List<BigDecimal> lfolio=query.list();
		if(!lfolio.isEmpty()){
			return lfolio.get(0).intValue();
		}
		return null;
	}

	@Override
	public void save(Compra compra) {
		compra.setFechaAplicacionPromocion(new Date());
		sessionFactory.getCurrentSession().save(compra);	
	}

	@Override
	public void update(Compra compra) {
		compra.setFechaAplicacionPromocion(new Date());
		sessionFactory.getCurrentSession().update(compra);	
		
	}

	@Override
	public List<Object[]> getReporte(DetachedCriteria criteria) {
		Session session=sessionFactory.getCurrentSession();
		return (List<Object[]>) criteria.getExecutableCriteria(session).setMaxResults(1000).list();
	}
	
	@Override
	public List<Compra> findByCriteria(DetachedCriteria criteria) {
		Session session=sessionFactory.getCurrentSession();
		List<Compra> results = criteria.getExecutableCriteria(session).setMaxResults(1000).list();
		return results;
	}

	@Override
	public Double getMontoComprasByDateRange(String cuenta, Date fechaIn,Date fechaFin) {
		Query query =sessionFactory.getCurrentSession().createQuery(
				"select sum(c.monto) from Compra c where c.cuenta = ? and c.fechaCompra between ? and  ?");
		query.setString(0, cuenta);
		query.setDate(1, fechaIn);
		query.setDate(2, fechaFin);
		return (Double) query.list().get(0);
	}
	
	@Override
	public Double getMontoComprasByDateRange(String cuenta, Date fechaIn,Date fechaFin,String tipoTrans) {
		Query query =sessionFactory.getCurrentSession().createQuery(
				"select sum(c.monto) from Compra c where c.cuenta = ? and c.fechaCompra between ? and  ? and c.tipoTransaccion = ?");
		query.setString(0, cuenta);
		query.setDate(1, fechaIn);
		query.setDate(2, fechaFin);
		query.setString(3, tipoTrans);
		return (Double) query.list().get(0);
	}

}
