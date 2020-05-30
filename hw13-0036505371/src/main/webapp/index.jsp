<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Home</title>
	</head>
   <body style="background-color:${pickedBgCol};">
   	<a href="colors.jsp">Background color chooser</a> &nbsp;
   	<a href="trigonometric?a=0&b=90">Trigonometric</a> &nbsp;
   	<a href="stories/funny.jsp">Funny story</a> &nbsp;
   	<a href="report.jsp">OS usage</a> &nbsp;
   	<a href="powers?a=1&b=100&n=3">Powers</a> &nbsp;
   	<a href="appinfo.jsp">App info</a>
    
    <br><br>
    <form action="trigonometric" method="GET">
 		Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 		Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
	
	<p>Glasaj za svoj najdraži <b>EX-YU bend</b> 
	<a href="glasanje">ovdje</a>!
   </body>
</html>