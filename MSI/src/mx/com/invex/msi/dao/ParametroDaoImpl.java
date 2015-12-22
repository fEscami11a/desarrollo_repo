package mx.com.invex.msi.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.invex.msi.model.Parametro;
@Repository("parametroDaoImpl")
public class ParametroDaoImpl implements ParametroDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Parametro getParamById(String clave) {
		return (Parametro)sessionFactory.getCurrentSession().get(Parametro.class, clave);
	}

}
