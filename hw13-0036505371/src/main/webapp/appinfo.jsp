<%@ page import="java.text.SimpleDateFormat, java.util.Date" 
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
	<head>
		<title>App info</title>
	</head>
	<body style="background-color:${pickedBgCol};">
	<a href="index.jsp">Home page</a>
	<h1>Time info</h1>
   	<p>Time elapsed since starting app: <b>
   	<% 
   	long startTime = (Long) request.getServletContext().getAttribute("time");
   	long timeElapsed = System.currentTimeMillis() - startTime;
   	long years = timeElapsed / (1000 * 60 * 60 * 24 * 365);
   	timeElapsed -= years * 1000 * 60 * 60 * 24 * 365 ;
   	long months = timeElapsed / (1000 * 60 * 60 * 24 * 30);
   	timeElapsed -= months * 1000 * 60 * 60 * 24 * 30;
   	long weeks = timeElapsed / (1000 * 60 * 60 * 24 * 7);
   	timeElapsed -= weeks * 1000 * 60 * 60 * 24 * 7;
	long days = timeElapsed / (1000 * 60 * 60 * 24);
   	timeElapsed -= days * 1000 * 60 * 60 * 24;
   	long hours = timeElapsed / (1000 * 60 * 60);
   	timeElapsed -= hours * 1000 * 60 * 60;
   	long minutes = timeElapsed / (1000 * 60);
   	timeElapsed -= minutes * 1000 * 60;
   	long seconds = timeElapsed / (1000);
   	timeElapsed -= seconds * 1000;

	out.print(years + " years, " + months + " months, " + weeks + " weeks, " 
			+ days + " days, " + hours + " hours, " + minutes + " minutes, "
			+ seconds + " seconds and " + timeElapsed 
			+ " milliseconds.");
   	%>	
   	</b>
   	</body>
</html>