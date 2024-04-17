<%@ page import="java.util.List" %>
<%@ page import="com.bjpowernode.oa.bean.Dept" %>
<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>部门列表页面</title>
</head>
<body>

<h3>欢迎<%=session.getAttribute("username")%></h3>
<a href="<%=request.getContextPath()%>/user/exit">[退出系统]</a>

<script type="text/javascript">
    function del(dno){
        var ok = window.confirm("亲，删了不可恢复哦！");
        if(ok){
            document.location.href = "<%=request.getContextPath()%>/dept/delete?deptno=" + dno;
        }
    }
</script>

<h1 align="center">部门列表</h1>
<hr >
<table border="1px" align="center" width="50%">
    <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>操作</th>
    </tr>

    <%
        //从请求域中获取集合
        List<Dept> deptlist = (List<Dept>)request.getAttribute("deptlist");
        for (Dept dept:deptlist)
        {
            int i = 0;
    %>

    <tr>
    <td><%=++i%></td>
    <td><%=dept.getDeptno()%></td>
    <td><%=dept.getDname()%></td>
    <td>
        <a href="javascript:void(0)" onclick="del(<%=dept.getDeptno()%>)">删除</a>
        <a href="<%=request.getContextPath()%>/dept/detail?f=edit&dno=<%=dept.getDeptno()%>">修改</a>
        <a href="<%=request.getContextPath()%>/dept/detail?f=detail&dno=<%=dept.getDeptno()%>">详情</a>
    </td>
</tr>

    <%
        }
    %>

</table>

<hr >
<a href="<%=request.getContextPath()%>/old/add.jsp.jsp">新增部门</a>

</body>
</html>
