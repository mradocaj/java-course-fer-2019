<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
<head>
		<title>Main</title>
		
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
 <c:forEach var="file" items="${files}">
   	<li><a href="drawing?file=${file}">${file}</a></li>
 </c:forEach>
 <br><br>
 <form action="${pageContext.request.contextPath}/newFile" 
		method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Ime datoteke</span><input type="text" name="name" 
		  value='<c:out value="new.jvd"/>' size="30">
		 </div>
		</div>
		<br>
		
		<div>
		 <div>
		  <span class="formLabel">Sadržaj datoteke</span>
		  
		  <textarea name="text" cols="60" rows="20"></textarea>
		  
		 </div>
		</div>
		<br>
		
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Spremi">
		</div>
		
		</form>
 </body>
</html>