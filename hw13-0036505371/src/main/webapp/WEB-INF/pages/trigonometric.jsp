<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Trigonometric</title>
	</head>
	<body style="background-color:${pickedBgCol};">
	<a href="index.jsp">Home page</a>
	<br><br>
   	<table border="2" bgcolor="#FFFFFF">
   	<tr><td><b>x</b></td><td><b>sin(x)</b></td><td><b>cos(x)</b></td></tr>
   	<c:forEach var="entry" items="${result}">
   	<tr>
   	<td>${entry.getAngle()}</td>
   	<td>${entry.getSin()}</td>
   	<td>${entry.getCos()}</td>
   	</tr>
   	</c:forEach>
   	</table>
   	</body>
</html>