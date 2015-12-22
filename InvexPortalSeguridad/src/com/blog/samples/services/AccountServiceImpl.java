package com.blog.samples.services;

import java.util.List;

import mx.com.invex.seguridad.entidades.Usuario;
import mx.com.invex.seguridad.sevice.UsuarioService;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.samples.webservices.Account;
import com.blog.samples.webservices.EnumAccountStatus;
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	UsuarioService usuarioService;
	
	@Override
	public Account getAccountDetails(String accountNumber) {
		/* hard coded account data - in reality this data would be retrieved  
         * from a database or back end system of some sort */  
        Account account = new Account();  
        account.setAccountNumber("12345");  
        account.setAccountStatus(EnumAccountStatus.ACTIVE);  
        account.setAccountName("Joe Bloggs");  
        account.setAccountBalance(3400);  
        
        DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
		usrCrit.add(Restrictions.eq("nombreusuario", "klgtsrjkl"));
		usrCrit.add(Restrictions.eq("estatus",true));
		List<Usuario> users =usuarioService.findByCriteria(usrCrit);
        return account;  

	}

}
