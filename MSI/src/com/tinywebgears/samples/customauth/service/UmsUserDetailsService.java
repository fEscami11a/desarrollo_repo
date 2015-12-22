package com.tinywebgears.samples.customauth.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UmsUserDetailsService implements UserDetailsService
{
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMIN";
    /** The JNDI name of the DataSource to use */
	   protected String dsJndiName;
	 
	   /** The sql query to obtain the user roles */
	   protected String rolesQuery;
	   
	   

    private Logger logger = LoggerFactory.getLogger(UmsUserDetailsService.class);

   

  

    public String getDsJndiName() {
		return dsJndiName;
	}

	public void setDsJndiName(String dsJndiName) {
		this.dsJndiName = dsJndiName;
	}


	public String getRolesQuery() {
		return rolesQuery;
	}

	public void setRolesQuery(String rolesQuery) {
		this.rolesQuery = rolesQuery;
	}

	

  
  

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        logger.debug("Loading user by name: " + username);
        
        if(username== null || username.trim().isEmpty()){
        	throw new UsernameNotFoundException("Username " + username + " not found!");
        }
              Connection conn = null;
 	     
 	      PreparedStatement ps = null;
 	      ResultSet rs = null;
 	     try
  	      {
        InitialContext ctx = new InitialContext();
	         DataSource ds = (DataSource) ctx.lookup(dsJndiName);
	         conn = ds.getConnection();
	         // Get the user role names
	         ps = conn.prepareStatement(rolesQuery);
	         try
	         {
	            ps.setString(1, username);
	         }
	         catch(ArrayIndexOutOfBoundsException ignore)
	         {
	            // The query may not have any parameters so just try it
	         }
	         rs = ps.executeQuery();
	         List<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
	         while(rs.next()){
	        	 String role=rs.getString(1);
	        	 logger.debug("role "+role);
	        	 userAuthorities.add(new GrantedAuthorityImpl(role));
	         }
	         
	         
	         User user = new User(username, "password", true, true,
	                    true, true, userAuthorities);
	           
	            return user;
	         
	         
  	    }
  	      catch(Exception ex)
  	      {
  	        logger.error("SQL failure", ex);
  	       
  	      }
  	      finally
  	      {
  	         if( rs != null )
  	         {
  	            try
  	            {
  	               rs.close();
  	            }
  	            catch(SQLException e)
  	            {}
  	         }
  	         if( ps != null )
  	         {
  	            try
  	            {
  	               ps.close();
  	            }
  	            catch(SQLException e)
  	            {}
  	         }
  	         if( conn != null )
  	         {
  	            try
  	            {
  	               conn.close();
  	            }
  	            catch (Exception ex)
  	            {}
  	         }
  	      }
        
//        if (username != null && username.equals("user"))
//        {
//            Vector<GrantedAuthority> userAuthorities = new Vector<GrantedAuthority>();
//            userAuthorities.add(new GrantedAuthorityImpl(ROLE_USER));
////            User user = new User("user", "Et6pb+wgWTVmq3VpLJlJWWgzrck=" /* SHA-1 encoded of "user" */, true, true,
////                    true, true, userAuthorities.toArray(new GrantedAuthority[0]));
//            User user = new User("user", getPassword(), true, true,
//                  true, true, userAuthorities.toArray(new GrantedAuthority[0]));
//          
//            currentUser.set(user);
//            return user;
//        }
//        if (username != null && username.equals("admin"))
//        {
//            Vector<GrantedAuthority> userAuthorities = new Vector<GrantedAuthority>();
//            userAuthorities.add(new GrantedAuthorityImpl(ROLE_USER));
//            userAuthorities.add(new GrantedAuthorityImpl(ROLE_ADMINISTRATOR));
////            User user = new User("admin", "0DPiKuNIrrVmD8IUCuw1hQxNqZc=" /* SHA-1 encoded of "admin" */, true, true,
////                    true, true, userAuthorities.toArray(new GrantedAuthority[0]));
//            User user = new User("admin", getPassword(), true, true,
//                    true, true, userAuthorities.toArray(new GrantedAuthority[0]));
//            currentUser.set(user);
//            return user;
//        }
        throw new UsernameNotFoundException("Username " + username + " not found!");
    }

//    public User getCurrentUser()
//    {
//        return currentUser.get();
//    }
//
//    public void setCurrentUser(User user)
//    {
//        currentUser.set(user);
//    }
//
//    public String getPassword()
//    {
//        return currentPassword.get();
//    }
//
//    public void setPassword(String password)
//    {
//        currentPassword.set(password);
//    }
}
