<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Popis zapisa</title>
		
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
		
		<h3>Popis zapisa autora <b>${nick}</b>:</h3>
		
		<c:choose>
		<c:when test="${entries.size()==0}">
		<font size="3">Autor <b>${nick}</b> nema zapisa.<br></font>
		</c:when>
		<c:otherwise>
		<ul>
		<c:forEach var="entry" items="${entries}">
		<li><a href="${pageContext.request.contextPath}/servleti/author/${nick}/${entry.id}">
		<font size="3">${entry.title}</font></a></li></c:forEach>
		</ul>
		</c:otherwise>
		</c:choose>
		
		<br>
		<hr>
		<br>
		<c:choose>
		<c:when test="${providedNick}">
		<a href="${pageContext.request.contextPath}/servleti/author/${nick}/new">
		<font size="3">Novi zapis bloga</font></a>
		<br><br>
		</c:when>
		</c:choose>
		
		<a href="${pageContext.request.contextPath}/index.jsp"><font size="3">
		Nazad na početnu stranicu</font></a>
	</body>
</html>