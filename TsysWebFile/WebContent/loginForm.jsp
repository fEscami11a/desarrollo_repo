<%@ page import="org.securityfilter.example.Constants"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><%=Constants.LOGIN_TITLE%></title>
</head>
<body>

Pantalla para subir archivos .txt</i>
para realizar operaciones en Tsys</i>.

<form id="<%=Constants.LOGIN_FORM_ID%>" action="<%=response.encodeURL(Constants.LOGIN_FORM_ACTION)%>" method="POST">

Username:
<input type="text"
   name="<%=Constants.LOGIN_USERNAME_FIELD%>"
  
><p>

Password:
<input type="password"
   name="<%=Constants.LOGIN_PASSWORD_FIELD%>"
   
><p>

<input type="Submit">

</form>
</body>
</html>