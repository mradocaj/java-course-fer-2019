<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Registracija</title>
		
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
			<h2>&nbsp;Registracija</h2>
		</header>
		
		<br>
		<form action="register" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Ime</span><input type="text" name="firstName" 
		  value='<c:out value="${form.firstName}"/>' size="20">
		 </div>
		 <c:if test="${form.hasError('firstName')}">
		 <div class="greska"><c:out value="${form.getError('firstName')}"/></div>
		 </c:if>
		</div>
		<br>
		
		<div>
		 <div>
		  <span class="formLabel">Prezime</span><input type="text" name="lastName" 
		  value='<c:out value="${form.lastName}"/>' size="20">
		 </div>
		 <c:if test="${form.hasError('lastName')}">
		 <div class="greska"><c:out value="${form.getError('lastName')}"/></div>
		 </c:if>
		</div>
		<br>
		
		<div>
		 <div>
		  <span class="formLabel">Email</span><input type="text" name="email" 
		  value='<c:out value="${form.email}"/>' size="20">
		 </div>
		 <c:if test="${form.hasError('email')}">
		 <div class="greska"><c:out value="${form.getError('email')}"/></div>
		 </c:if>
		</div>
		<br>
		
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
		<br>	
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Registriraj se">
		  <input type="submit" name="method" value="Odustani">
		</div>
		
		</form>

	</body>
</html>