<%@ page import="java.util.List" session="true"
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
 <head>
 <title>Rezultati glasanja</title>
 <style type="text/css">
 table.rez td {text-align: center;}
 </style>
 </head>
 <body>
<a href="index.html">Home page</a>
 <h1>Rezultati glasanja</h1>
 <p>Ovo su rezultati glasanja.</p>
 <table border="1" class="rez">
 <thead><tr><th>Izbor</th><th>Broj glasova</th></tr></thead>
 <tbody>
 <c:forEach var="result" items="${result}">
   	<tr><td>${result.getTitle()}</td><td>${result.getVotes()}</td></tr>
 </c:forEach>
 </tbody>
 </table>

 <h2>Grafički prikaz rezultata</h2>
 <img alt="Pie-chart" src="glasanje-grafika?pollId=${pollId}" width="400" height="400" />

 <h2>Rezultati u XLS formatu</h2>
 <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollId=${pollId}">ovdje</a></p>

 <h2>Razno</h2>
 <p>Primjeri pobjedničkih djela:</p>
 <ul>
 <c:forEach var="first" items="${first}">
   	<li><a href="${first.getLink() }" target="_blank">${first.getTitle() }</a></li>
 </c:forEach>
 </ul>
 </body>
</html>