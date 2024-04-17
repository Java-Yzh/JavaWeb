<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>菜品列表页面</title>
</head>
<body>

<script type="text/javascript">
    function del(id){
        var ok = window.confirm("亲，删了不可恢复哦！");
        if(ok){
            document.location.href = "${pageContext.request.contextPath}/dish/delete?id=" + id;
        }
    }
</script>

<h1 >菜品列表</h1>
<hr >
<table>

    <tr>
        <th>序号</th>
        <th>菜品编号</th>
        <th>菜品名称</th>
        <th>操作</th>
    </tr>

    <c:forEach items="${dishs}" var="dish" varStatus="dishStatus">
        <tr>
            <td>${dishStatus.count}</td>
            <td>${dish.id}</td>
            <td>${dish.name}</td>
            <td>
                <a href="javascript:void(0)" onclick="del(${dish.id})">删除</a>
                <a href="/food2/dish/edit?id=${dish.id}">修改</a>
            </td>
        </tr>
    </c:forEach>

</table>

<a href="/food2/add.jsp">新增菜品</a>

</body>
</html>
