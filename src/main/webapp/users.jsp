<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form action="users?action=" method="get">
    <p><select name="user">
        <option value="1">Чебурашка</option>
        <option selected value="2">Крокодил Гена</option>
    </select></p>
    <p><input type="submit" value="Отправить"></p>
</form>
<%--// недоделано--%>
</body>
</html>