<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Zapis</title>
		
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
		
		<br>
		<font size="3">Objavio: <b>${nick}</b>, ${entry.createdAt} h</font>
		<br>
		<font size="3">Posljednje uređivanje: ${entry.lastModifiedAt} h</font>
		<hr>
		<h2>${entry.title}</h2>
		<font size="3">${entry.text}</font>
		<br><br>
		<hr>
		<h3>Komentari:</h3>
		<c:choose>
		<c:when test="${comments.size()==0}">
			<font size="3">Zapis <i>${entry.title}</i> nema komentara. 
			Dodaj prvi komentar!</font>
			<br><br><hr>
			
		</c:when>
		<c:otherwise>
			<c:forEach var="comment" items="${comments}">
			<font size="3">${comment.usersEMail}, ${comment.postedOn} h:</font>
			<br><br>
			<font size="3">${comment.message}</font>
			<hr>
			</c:forEach>
		</c:otherwise>
		</c:choose>
		<h3>Dodaj komentar</h3>
		<form action="${pageContext.request.contextPath}/servleti/author/${nick}/${entry.id}" 
		method="post">
		
		<c:choose>
		<c:when test="${sessionScope['current.user.id']==null}">
			<div>
		 <div>
		  <span class="formLabel">E-mail</span><input type="text" name="email" 
		  value='<c:out value="${form.email}"/>' size="20">
		 </div>
		 <c:if test="${form.hasError('email')}">
		 <div class="greska"><c:out value="${form.getError('email')}"/></div>
		 </c:if>
		</div>
		</c:when>
		</c:choose>
		<br>
		
		<div>
		 <div>
		  <span class="formLabel">Komentar</span>
		  <textarea name="message" cols="50" rows="20">${form.message}</textarea>
		 </div>
		 <c:if test="${form.hasError('message')}">
		 <div class="greska"><c:out value="${form.getError('message')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Dodaj komentar">
		  <input type="submit" name="method" value="Odustani">
		</div>
		</form>
		<br><hr>
		<br>
		<c:if test="${atr}">
		<a href="${pageContext.request.contextPath}/servleti/author/${nick}/edit?id=${entry.id}">
		<font size="3">Uredi zapis <i>${entry.title}</i></font></a>
		<br><br>
		 </c:if>
		<a href="${pageContext.request.contextPath}/servleti/author/${nick}"><font size="3">
		Nazad na ostale zapise autora ${nick}</font></a>
		<br><br>
		<a href="${pageContext.request.contextPath}/index.jsp"><font size="3">
		Nazad na početnu stranicu</font></a>
	</body>
</html>