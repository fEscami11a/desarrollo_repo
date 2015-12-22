package mx.com.invex.msi.controller;

import javax.faces.bean.SessionScoped;

import mx.com.invex.msi.service.MyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SessionScoped
public class MyBean {

	@Autowired
	MyService myService;
	
	public String getMessage(){
		return myService.sayHi();
	}
}
