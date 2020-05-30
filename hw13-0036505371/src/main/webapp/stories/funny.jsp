<%@ page import="java.lang.Math"
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<%! 
private void randomColor(javax.servlet.jsp.JspWriter out) throws java.io.IOException {
	char[] letters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	String color = "#";
	for (int i = 0; i < 6; i++) {
	    color += String.valueOf(letters[(int) Math.floor(Math.random() * 16)]);
	  }
	out.print(color);
}
%>
<html>
	<head>
		<title>Funny story</title>
	</head>
	<body style="background-color:${pickedBgCol};">
   	<font color="<%randomColor(out); %>">
   	<h1>Poklon mami i tati</h1>
   	<p>Kad smo moja sestra i ja bile male, odlučile smo da želimo nekako iznenaditi mamu i tatu.
   	Jedini problem je bio što nismo znale kako to učiniti.</p>
   	<p>Razmišljale smo i razmišljale te na kraju došle do odlične ideje - uredit ćemo njihov
   	novi bračni krevet. Drvena daska kreveta činila nam se jako prazno pa smo ju odlučile urediti
   	sa čekićem i šerafcigerima. Tako smo rezbarile drvo i crtale različite crteže i oblike. Kad smo
   	napokon završile, pozvale smo mamu i tatu da dođu vidjeti poklon.</p>
   	<p><br>Malo je reći da nisu bili presretni.</p>
   	</font>
   	</body>
</html>