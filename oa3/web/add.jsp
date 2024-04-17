<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>新增部门</title>
    <%--    <base href="http://localhost:8080/oa/">--%>
    <%--    设置整个网页的基础路径，下面代码中的路径中不以"/"开头的，在前面拼上以下基础路径--%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<h3>欢迎${username}</h3>
<body>
<h1>新增部门</h1>
<hr >
<form action="dept/save" method="post">
    部门编号<input type="text" name="deptno"/><br>
    部门名称<input type="text" name="dname"/><br>
    部门位置<input type="text" name="loc"/><br>
    <input type="submit" value="保存"/><br>
</form>
</body>
</html>