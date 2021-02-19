<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>

Add/edit meal

<form action="meals" method="post" >
<%--    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal">--%>
        DateTime<input type="datetime-local" name="datetimeInForm" value="${meal.dateTime}">
        <br>
        Description<input type="text" name="descriptionInForm" value="${meal.description}">
        <br>
        Calories<input type="number" name="caloriesInForm" value="${meal.calories}">
        <br>
        <input type="number" hidden name="id" value=${meal.id}>
        <input type="submit" >
</form>

</body>
</html>
