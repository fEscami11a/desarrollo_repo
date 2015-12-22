package mx.com.invex.seguridad.sevice;

import java.util.List;

import mx.com.invex.seguridad.entidades.Usuario;

import org.hibernate.criterion.DetachedCriteria;

public interface UsuarioService {
	List<Usuario> findByCriteria(DetachedCriteria criteria);

	void delete(Usuario usr);

	void merge(Usuario usr);
}
