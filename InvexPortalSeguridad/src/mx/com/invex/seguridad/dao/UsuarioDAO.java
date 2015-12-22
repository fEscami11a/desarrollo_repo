package mx.com.invex.seguridad.dao;

import java.util.List;

import mx.com.invex.seguridad.entidades.Usuario;

import org.hibernate.criterion.DetachedCriteria;

public interface UsuarioDAO {

	List<Usuario> findByCriteria(DetachedCriteria criteria);

	void delete(Usuario usr);

	void merge(Usuario usr);

}