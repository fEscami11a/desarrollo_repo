<%@ page import="org.securityfilter.example.Constants"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><%=Constants.SECURE_TITLE%></title>
</head>
<body>

<h1><%=Constants.SECURE_TITLE%></h1>

<%@include file="/menu.jsp" %>

Welcome <i><%=request.getRemoteUser()%></i>, you are viewing a secure page.

<%
   String postedValue = request.getParameter(Constants.HOME_POST_FIELD);
   if (postedValue != null) {
%>
<h2>You POSTed a value from the Home Page!</h2>
<form name="<%=Constants.SECURE_POSTED_VALUE_FORM%>" action="">
The value you just posted was:
<ul>
   <li>
      <%=postedValue%>
      <input type="hidden" name="<%=Constants.SECURE_POSTED_VALUE_FIELD%>" value="<%=postedValue%>"/>
   </li>
</ul>
<%
      String lastPostedValue = (String) session.getAttribute(Constants.SECURE_LAST_POSTED_VALUE_FIELD);
      if (lastPostedValue != null) {
%>
The last value you posted in your current session was:
<ul>
   <li>
      <%=lastPostedValue%>
      <input type="hidden" name="<%=Constants.SECURE_LAST_POSTED_VALUE_FIELD%>" value="<%=lastPostedValue%>"/>
   </li>
</ul>
<%
      }
      // save the last posted value in the session
      session.setAttribute(Constants.SECURE_LAST_POSTED_VALUE_FIELD, postedValue);
%>
</form>
<%
   }
%>

</body>
</html>