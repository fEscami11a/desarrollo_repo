package mx.com.invex.msi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.com.invex.msi.model.User;
import mx.com.invex.msi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class UserBean {
	
@Autowired
UserService us;

@PostConstruct
public void init() {
populateUsers();
}



private List<User> userList= new ArrayList<User>();
private boolean update;
private User user;

public User getUser() {
	return user;
}



public void setUser(User user) {
	this.user = user;
}



private void populateUsers() {
    userList = us.getAllUsers();
}



public UserService getUs() {
	return us;
}

public void setUs(UserService us) {
	this.us = us;
}

public List<User> getUserList() {
//	userList = new ArrayList<User>();
//	userList.addAll(getUs().getAllUsers());
	return userList;
}

public void setUserList(List<User> userList) {
	this.userList = userList;
} 


public String newButtonPressed() {
    update = false;
    user = new User();
    return "editUser";
}

public String editButtonPressed() {
    update = true;
    return "editUser";
}

public String saveUser() {
    if (update) {
        us.updateUser(user);
        populateUsers();
    } else {
        us.insertUser(user);
        populateUsers();
    }
    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration success", "success"); 
    FacesContext.getCurrentInstance().addMessage(null, msg);
    return "index";
}

public void deleteUser() {
    us.deleteUser(user.getUsername());
    populateUsers();
}


}
