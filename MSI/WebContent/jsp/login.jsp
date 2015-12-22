<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="mx.com.invex.msi.util.MSIConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Sistema de Meses sin intereses</title>
</head>
<body>
<form id="login_form" name="login_form" method="post"
        action="j_security_check" enctype="application/x-www-form-urlencoded">
       
             
            <p><h3>Login Invex - MSI <%if(MSIConstants.desa){ %>Desarrollo Version 2.0<% }%></h3></p>
       
       
            <p>
                <label for="username"> Username</label><br /> <input id="username"
                    type="text" name="j_username" size="20" />
            </p>
            <p>
                <label for="password"> Password</label><br /> <input id="password"
                    type="password" name="j_password" value="" size="20" />
            </p>
            
                <input id="submit" type="submit" name="submit" value="Login"
                    class="buttonmed" />
           
    </form>
</body>
</html>