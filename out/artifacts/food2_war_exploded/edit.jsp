<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改页面</title>
</head>
<body>

<h1>修改菜品</h1>
<hr >
<form action="/food2/dish/modify" method="get">
    菜品编号<input type="text" name="id" value="${editdish.id}" readonly /><br>
    菜品名称<input type="text" name="name" value="${editdish.name}"/><br>
    <input type="submit" value="修改"/><br>
</form>

</body>
</html>
