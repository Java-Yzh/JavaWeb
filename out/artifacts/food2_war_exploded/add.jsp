<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增菜品</title>
</head>
<body>
<h1>新增菜品</h1>
<hr >
<form action="/food2/dish/save" method="post">
  菜品编号<input type="text" name="id"/><br>
  菜品名称<input type="text" name="food"/><br>
  <input type="submit" value="保存"/><br>
</form>
</body>
</html>
