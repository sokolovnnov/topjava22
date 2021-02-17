<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<h3><a href="meals?action=add">Add meal</a> </h3>
    <table border="1">
        <tr>
            <th>description</th>
            <th>time</th>
            <th>calories</th>
            <th>excess</th>
        </tr>
        <c:forEach var="m" items="${requestScope.meals}">
        <tr>
            <td>${m.description}</td>
            <td>
                <fmt:parseDate value="${ m.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
            </td>
            <td>${m.calories}</td>
            <td>${m.excess}</td>
            <td><a href="meals?action=update&id=${m.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${m.id}">Delete</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>