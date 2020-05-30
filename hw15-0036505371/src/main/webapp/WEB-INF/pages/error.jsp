<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Greška</title>
		
		<style type="text/css">
		header {
		  margin: 0;
		  border: 1px solid grey;
  		  border-radius: 5px;
		}
		html {
			font-family: "Verdana"
		}
		</style>
	</head>

	<body>
	<header style="background-color:powderblue;">
			<c:choose>
			<c:when test="${sessionScope['current.user.id']==null}">
			<h2>&nbsp;Nisi prijavljen/a.</h2>
	
			</c:when>
			
			<c:otherwise>
			<h2>
			&nbsp;${sessionScope['current.user.fn']}
			${sessionScope['current.user.ln']}
			&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/servleti/logout">Odjavi se</a>
			</h2>
			</c:otherwise>
			</c:choose>
		</header>
		
		<h2>&nbsp;Greška</h2>
		<br>
		<font size="3">
		Navedena stranica ne postoji ili nemate dozvolu za pristup.
		</font>
		<br><br>
		<hr>
		<br>
		<a href="${pageContext.request.contextPath}/index.jsp"><font size="3">Nazad na početnu stranicu</font></a>
	</body>
</html>