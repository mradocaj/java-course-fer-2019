<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Error</title>
	</head>
	<body style="background-color:${pickedBgCol};">
	<a href="index.jsp">Home page</a>
   	<h1>Error</h1>
   	<p>Invalid parameters given. Parameters should be:
   	<ul>
   	<li><b>a</b> - integer from <b>[-100, 100]</b></li>
   	<li><b>b</b> - integer from <b>[-100, 100]</b></li>
   	<li><b>n</b> - integer from <b>[1, 5]</b></li>
   	</ul>   	
   	</body>
</html>