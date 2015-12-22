<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>SecurityFilter Example Application: Forbidden Secure Page</title>
</head>
<body>
<h1>SecurityFilter Example Application: Forbidden Secure Page</h1>
<%@include file="/menu.jsp" %>
Welcome <%=request.getRemoteUser()%>, you are viewing a secure page. But something went wrong because you aren't authorized to see it!</body>
</html>