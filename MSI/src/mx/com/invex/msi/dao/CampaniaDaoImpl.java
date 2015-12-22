package mx.com.invex.msi.dao;

import java.util.Date;
import java.util.List;

import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.Producto;
import mx.com.invex.msi.model.ProductoTs2;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("campaniaDaoImpl")
public class CampaniaDaoImpl implements CampaniaDao{

	private static final Logger logger = Logger.getLogger(CampaniaDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public List<Campania> getCampaniasMasivas() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Campania.class);
		Date diahoy = new Date();
		criteria.add(Restrictions.and(Restrictions.le("fechaInicial", diahoy), Restrictions.ge("fechaMaximaRegistro", diahoy)));
		criteria.add(Restrictions.eq("tipoCampania","masiva"));
		return criteria.list();
	}

	
	public Campania getCampaniaInternal() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Campania.class);
		criteria.add(Restrictions.eq("tipoCampania", "compras.internal"));
		return (Campania) criteria.list().get(0);
	}

	
	public List<Campania> getCampaniasComercios() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Campania.class);
		Date diahoy = new Date();
		criteria.add(Restrictions.and(Restrictions.le("fechaInicial", diahoy), Restrictions.ge("fechaMaximaRegistro", diahoy)));
		criteria.add(Restrictions.eq("tipoCampania","comercio"));
		return criteria.list();
	}

	
	public Campania getCampaniaNalProg0() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Campania.class);
		criteria.add(Restrictions.eq("tipoCampania", "compras.nal.prog.cero"));
		return (Campania) criteria.list().get(0);
	}
	
	
	public Campania getCampaniaById(Long id) {
		return (Campania)sessionFactory.getCurrentSession().get(Campania.class, id);
	}

	
	public List<Campania> getCampaniaByParams(Date fechaIn, Date fechaMaxReg,
			String tipoCampania, Producto producto) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Campania.class);
		if(fechaIn!=null && fechaMaxReg!=null){
			criteria.add(Restrictions.and(Restrictions.le("fechaInicial", fechaIn), 
				Restrictions.ge("fechaMaximaRegistro", fechaMaxReg)));
		}
		criteria.add(Restrictions.eq("tipoCampania",tipoCampania));
		if("comercio".equals(tipoCampania))
			criteria.createAlias("comercios","com", JoinType.LEFT_OUTER_JOIN);
		if(producto != null){
			criteria.createAlias("productos","p", JoinType.LEFT_OUTER_JOIN).add( Restrictions.eq("p.id",producto.getId()));
		}
		return  criteria.list();
	}
	
	public List<Campania> getCampaniaProdTs2(Date fechaIn, Date fechaMaxReg,
			String tipoCampania, ProductoTs2 producto) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Campania.class);
		criteria.add(Restrictions.and(Restrictions.le("fechaInicial", fechaIn), Restrictions.ge("fechaMaximaRegistro", fechaMaxReg)));
		criteria.add(Restrictions.eq("tipoCampania",tipoCampania));
		
		if(producto != null){
			criteria.createAlias("productosts2","p", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("p.id",producto.getId()));
		}
		return  criteria.list();
	}

	
	public List<Campania> getCampaniaByTipo(String tipoCamp) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Campania.class);
		criteria.add(Restrictions.eq("tipoCampania",tipoCamp));
		return  criteria.list();
	}
	

	public List<Object[]> getCampComboByTipo(String tipoCamp) {
		 Query qry= sessionFactory.getCurrentSession().createQuery("select camp.idCampania, camp.nombre from Campania camp where tipoCampania=?");
		 qry.setString(0, tipoCamp);
		List<Object[]> list =qry.list();
		return list;
	}
	
	public List<Object[]> getidCampByCT(String codTrans) {
		 Query qry= sessionFactory.getCurrentSession().createQuery("select camp.idCampania, camp.nombre from Campania camp where camp.codigoTransaccion1 =?");
		 qry.setString(0, codTrans);
		List<Object[]> list =qry.list();
		return list;
	}


	public void saveOrUpdate(Campania campania) {
		sessionFactory.getCurrentSession().saveOrUpdate(campania);
		
	}

	
	public void updatePromosCamp(Campania campania) {
		sessionFactory.getCurrentSession().merge(campania);
		
	}

	@Override
	public List<Campania> findByCriteria(DetachedCriteria criteria) {
		Session session=sessionFactory.getCurrentSession();
		List<Campania> results = criteria.getExecutableCriteria(session).list();
		return results;
	}
	
}
