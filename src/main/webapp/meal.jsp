<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Add meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add meal:</h2>

<form method="post" action="meals">

    ID:${meal.id}
    <br>
    <input type="number" hidden name="idInForm" value="${meal.id}"/><br>
    DateTime<input type="datetime-local" name="dateTimeInForm" value="${meal.dateTime}"><br>
    Description<input type="text" name="descriptionInForm" value="${meal.description}"><br>
    Calories<input type="number" name="caloriesInForm" value="${meal.calories}"><br>
    <input type="submit">

</form>
</body>
</html>