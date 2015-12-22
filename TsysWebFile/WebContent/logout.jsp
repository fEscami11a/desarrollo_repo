<%@ page import="org.securityfilter.example.Constants"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><%=Constants.LOGOUT_TITLE%></title>
</head>

<body>
<h1><%=Constants.LOGOUT_TITLE%></h1>
<%@include file="/menu.jsp" %>
<% session.invalidate(); %>
You have been logged out of the SecurityFilter example application.<p>
This operation was achieved with a simple call to <code>session.invalidate()</code>.
</body>

</html>