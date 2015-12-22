<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Error Page</title>
</head>
<body>
<% if(response.getStatus() == 500){ %>
<font color="red">Error: <%=exception.getMessage() %></font><br>
 
<%-- include login page --%>
<%}else {%>
El codigo de error es <%=response.getStatus() %><br>

<%} %>
Regresar a inicio: <a href="<%=request.getContextPath() %>/faces/home.xhtml">home page</a>
</body>
</html>