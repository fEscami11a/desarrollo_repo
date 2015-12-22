package mx.com.invex.msi.service;

import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService{

	@Override
	public String sayHi() {
		return "You are now using Spring + JSF =)!!!";
	}

}
