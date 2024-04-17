<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆失败</title>
    <%--    设置整个网页的基础路径，下面代码中的路径中不以"/"开头的，在前面拼上以下基础路径--%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>
登陆失败，请<a href="index.jsp">重新登录</a>
</body>
</html>
