package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.dao.UserDao;
import mx.com.invex.msi.model.User;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Transactional(readOnly=true)
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public void insertUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	public void updateUser(User user) {
		userDao.update(user);
		
	}

	@Transactional
	public void deleteUser(String user) {
		userDao.delete(user);
		
	}
	

}
