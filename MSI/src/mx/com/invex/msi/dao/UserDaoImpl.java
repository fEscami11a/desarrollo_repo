package mx.com.invex.msi.dao;

import java.util.List;

import mx.com.invex.msi.model.User;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	 public SessionFactory getSessionFactory() {
		  return sessionFactory;
		 }
	
	 public void setSessionFactory(SessionFactory sessionFactory) {
		  this.sessionFactory = sessionFactory;
		 }
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		 return sessionFactory.getCurrentSession().createQuery("from User").list();
	}
	
	public void delete(String username){
		User user = (User)sessionFactory.getCurrentSession().load(User.class,username);
		sessionFactory.getCurrentSession().delete(user);
	}
	
	public void update(User user){
		sessionFactory.getCurrentSession().merge(user);
	}
}
