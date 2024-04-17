<%@page contentType="text/html;charset=UTF-8"%>
<%--在index.jsp不创建session--%>
<%@page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>欢迎使用OA系统</title>
    <%--    设置整个网页的基础路径，下面代码中的路径中不以"/"开头的，在前面拼上以下基础路径--%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>
<%--<a href="<%=request.getContextPath()%>/dept/list">查看部门列表</a>--%>

<h1>LOGIN PAGE</h1>
<hr>
<form action="user/login" method="post">
    username:<input type="text" name="username">
    <br>
    password:<input type="password" name="password">
    <br>
    <input type="checkbox" name="f" value="1">十天内免登录<br>
    <input type="submit" value="login">
</form>
</body>
</html>