<%@ page import="com.bjpowernode.oa.bean.Dept" %>
<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>部门详情</title>
</head>
<h3>欢迎<%=session.getAttribute("username")%></h3>
<body>
<h1>部门详情</h1>
<%
  Dept dept = (Dept)request.getAttribute("dept");
  String deptno = dept.getDeptno();
  String dname = dept.getDname();
  String loc = dept.getLoc();
%>
<hr >
部门编号：<%=deptno%> <br>
部门名称：<%=dname%><br>
部门位置：<%=loc%><br>

<input type="button" value="后退" onclick="window.history.back()"/>
</body>
</html>