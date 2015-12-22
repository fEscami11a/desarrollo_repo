package mx.com.invex.msi.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.invex.msi.model.Bitacora;
@Repository("bitacoraDaoImpl")
public class BitacoraDaoImpl implements BitacoraDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void saveBitacora(Bitacora bitacora) {
		sessionFactory.getCurrentSession().save(bitacora);

	}

}
