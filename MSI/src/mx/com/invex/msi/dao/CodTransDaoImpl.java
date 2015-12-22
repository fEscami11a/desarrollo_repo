package mx.com.invex.msi.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.invex.msi.model.CodTrans;
import mx.com.invex.msi.model.User;
@Repository("codTransDaoImpl")
public class CodTransDaoImpl implements CodTransDao {

	@Autowired
	private SessionFactory sessionFactory;
	@SuppressWarnings("unchecked")
	@Override
	public List<CodTrans> getAllCodTrans() {
		return sessionFactory.getCurrentSession().createQuery("from CodTrans").list();
	}

	@Override
	public CodTrans getById(Long id) {
		return (CodTrans)sessionFactory.getCurrentSession().get(CodTrans.class, id);
	}

	@Override
	public void update(CodTrans codTrans) {
		sessionFactory.getCurrentSession().merge(codTrans);
		
	}

	@Override
	public void save(CodTrans codTrans) {
		sessionFactory.getCurrentSession().save(codTrans);
		
	}

	@Override
	public void delete(String codTrans) {
		CodTrans codtrans = (CodTrans)sessionFactory.getCurrentSession().load(CodTrans.class,codTrans);
		sessionFactory.getCurrentSession().delete(codtrans);
		
	}

}
