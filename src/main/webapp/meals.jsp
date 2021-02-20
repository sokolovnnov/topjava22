<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Java Enterprise (Topjava)</title>
</head>
<body>
<h3>Meals</h3>
<hr>
<h4><a href="meals?action=new">Add meal</a> </h4>

<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>excess</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${requestScope.getall}" var="me">
    <tr>
        <td>${me.dateTime}</td>
        <td>${me.description}</td>
        <td>${me.calories}</td>
        <td>${me.excess}</td>
        <td><a href="meals?action=update&id=${me.id}">Update</a></td>
        <td><a href="meals?action=delete&id=${me.id}">Delete</a> </td>
    </tr>
    </c:forEach>
</table>
<ul>
</ul>
</body>
</html>