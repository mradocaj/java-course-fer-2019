<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Početna stranica</title>
		
		<style type="text/css">
		.greska {
		   font-family: Arial, Helvetica, sans-serif;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		.vl {
  			border-left: 1px grey;
 			height: 500px;
		}
		
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
	
		<c:choose>
		<c:when test="${sessionScope['current.user.id']==null}">
		<h3>Prijava:</h3>
		<form action="main" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Nadimak</span><input type="text" name="nick" 
		  value='<c:out value="${form.nick}"/>' size="20">
		 </div>
		 <c:if test="${form.hasError('nick')}">
		 <div class="greska"><c:out value="${form.getError('nick')}"/></div>
		 </c:if>
		</div>
		<br>
		
		<div>
		 <div>
		  <span class="formLabel">Lozinka</span><input type="password" name="password" 
		  value='<c:out value="${form.passwordHash}"/>' size="20">
		 </div>
		 <c:if test="${form.hasError('password')}">
		 <div class="greska"><c:out value="${form.getError('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Prijava">
		  <input type="submit" name="method" value="Odustani">
		</div>
		</form>
		
		<br>
		<font size="3">
		Nisi registriran/a? Registriraj se <a href="register">ovdje!</a>
		</font>
		<br><br><hr>
		</c:when>
		
		</c:choose>

		<h3>Popis autora: </h3>
		<c:choose>
		<c:when test="${users.size()==0}">
		<font size="3">Nema registriranih autora!</font>
		<br>
		</c:when>
		<c:otherwise>
		<ul>
		<c:forEach var="user" items="${users}">
		<li><a href="author/${user.nick}"><font size="3">${user.nick}</font></a></li></c:forEach>
		</ul>
		</c:otherwise>
		</c:choose>

	</body>
</html>
