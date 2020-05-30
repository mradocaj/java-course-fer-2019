<%@ page session="true"
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Colors</title>
	</head>

	<body style="background-color:${pickedBgCol};">
	<a href="index.jsp">Home page</a>
	<br><br>
	     <a href="setcolor?color=white">WHITE</a> &nbsp;
	     <a href="setcolor?color=red">RED</a> &nbsp;
	     <a href="setcolor?color=green">GREEN</a> &nbsp;
	     <a href="setcolor?color=cyan">CYAN</a>
	</body>
</html>