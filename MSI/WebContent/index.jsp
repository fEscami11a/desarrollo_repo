<%@page import="mx.com.invex.msi.util.MSIConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:a4j="http://richfaces.org/a4j"
      xmlns:rich="http://richfaces.org/rich"> 
<head>
<title>Insert title here</title>
</head>
<body>
	<%
	if(MSIConstants.desa){
		session.setAttribute("desa", "desa"); 
	}%>
 <%request.getSession().invalidate();%>  
<%response.sendRedirect("faces/home.xhtml");%>  

</body>
</html>