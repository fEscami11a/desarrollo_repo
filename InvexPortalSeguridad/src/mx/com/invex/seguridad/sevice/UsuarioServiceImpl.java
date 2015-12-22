package mx.com.invex.seguridad.sevice;

import java.util.List;

import mx.com.invex.seguridad.dao.UsuarioDAO;
import mx.com.invex.seguridad.entidades.Usuario;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@EnableTransactionManagement
@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService{
@Autowired
UsuarioDAO usuarioDao;

@Transactional(readOnly=true)
public List<Usuario> findByCriteria(DetachedCriteria criteria) {
	return usuarioDao.findByCriteria(criteria);
}

@Transactional
public void delete(Usuario usr) {
	usuarioDao.delete(usr);
}

@Transactional
public void merge(Usuario usr) {
	usuarioDao.merge(usr);
	
}



}
