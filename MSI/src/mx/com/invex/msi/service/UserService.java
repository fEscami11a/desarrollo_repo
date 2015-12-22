package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.model.User;


public interface UserService {
public List<User> getAllUsers();
public void insertUser(User user);
public void updateUser(User user);
public void deleteUser(String user);
}
