<%@ page import="com.bjpowernode.oa.bean.Dept" %>
<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>修改部门</title>
</head>
<h3>欢迎<%=session.getAttribute("username")%></h3>
<body>
<h1>修改部门</h1>
<hr >
<%
  Dept dept = new Dept();
  dept = (Dept) request.getAttribute("dept");
%>
<form action="<%=request.getContextPath()%>/dept/modify" method="get">
  部门编号<input type="text" name="deptno" value="<%=dept.getDeptno()%>" readonly /><br>
  部门名称<input type="text" name="dname" value="<%=dept.getDname()%>"/><br>
  部门位置<input type="text" name="loc" value="<%=dept.getLoc()%>"/><br>
  <input type="submit" value="修改"/><br>
</form>
</body>
</html>