<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
<head>
	<title>Glasanje</title>
</head>
 <body style="background-color:${pickedBgCol};">
 <a href="index.jsp">Home page</a>
 <h1>Glasanje za omiljeni EX-YU bend:</h1>
 <p>Od sljedećih EX-YU bendova, koji Vam je najdraži? Kliknite na link kako biste
glasali!</p>
 <ol>
 <c:forEach var="band" items="${bands}">
   	<li><a href="glasanje-glasaj?id=${band.getId()}">${band.getName()}</a></li>
 </c:forEach>
 </ol>
 </body>
</html>