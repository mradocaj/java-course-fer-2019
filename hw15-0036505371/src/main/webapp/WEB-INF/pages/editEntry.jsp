<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
		
		<h3>${title} zapis</h3>
		<br>
		<form action="${pageContext.request.contextPath}/servleti/author/${nick}/${atr}?id=${id}" 
		method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Naslov</span><input type="text" name="title" 
		  value='<c:out value="${form.title}"/>' size="30">
		 </div>
		 <c:if test="${form.hasError('title')}">
		 <div class="greska"><c:out value="${form.getError('title')}"/></div>
		 </c:if>
		</div>
		<br>
		
		<div>
		 <div>
		  <span class="formLabel">Tekst</span>
		  
		  <textarea name="text" cols="60" rows="20">${form.text}</textarea>
		  
		 </div>
		 <c:if test="${form.hasError('text')}">
		 <div class="greska"><c:out value="${form.getError('text')}"/></div>
		 </c:if>
		</div>
		<br>
		
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Objavi">
		  <input type="submit" name="method" value="Odustani">
		</div>
		
		</form>

	</body>
</html>