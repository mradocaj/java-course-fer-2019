<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
<head>
	<title>Glasanje</title>
</head>
 <body>
 <a href="index.html">Home page</a>
 <h1>${pollDescription.getTitle() }</h1>
 <p>${pollDescription.getMessage() }</p>
 <ol>
 <c:forEach var="pollEntry" items="${pollList}">
   	<li><a href="glasanje-glasaj?id=${pollEntry.getId()}&pollId=${pollEntry.getPollId()}">
   	${pollEntry.getTitle()}</a></li>
 </c:forEach>
 </ol>
 </body>
</html>