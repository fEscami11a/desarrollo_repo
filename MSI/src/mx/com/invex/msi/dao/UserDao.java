package mx.com.invex.msi.dao;

import java.util.List;

import mx.com.invex.msi.model.User;

public interface UserDao {

	public List<User> getAllUsers();
	public void delete(String username);
	public void update(User user);
	
}
