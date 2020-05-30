<%@ page import="java.util.List" session="true"
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
 <head>
 <title>Crtež</title>
 </head>
 <body>
<a href="${pageContext.request.contextPath}/main">Home page</a>
 <h1>${name}</h1>
 <br>Broj linija: ${lines}
 <br>Broj kružnica: ${circles}
 <br>Broj krugova: ${filledCircles}
 <br>Broj trokuta: ${triangles}
<br>
<img alt="Crtež" src="crtaj"/>

 </body>
</html>