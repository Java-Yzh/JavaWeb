package com.bjpowernode.oa;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeptListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //获取应用的根路径
        String contextPath = request.getContextPath();
        //设置响应的内容类型以及字符集。防止中文乱码问题。
        response.setContentType("text/html;charset = UTF-8");
        PrintWriter out = response.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("    <head>");
        out.print("        <meta charset='utf-8'>");
        out.print("        <title>部门列表页面</title>");

        out.print("<script type='text/javascript'>");
        out.print(" function del(dno){");
        out.print("var ok = window.confirm('亲，删了不可恢复哦！');");
        out.print(" if(ok){");
        out.print(" document.location.href = '"+contextPath+"/dept/delete?deptno=' + dno;");
        out.print(" }");
        out.print("}");
        out.print("</script>");

        out.print("    </head>");
        out.print("    <body>");
        out.print("        <h1 align='center'>部门列表</h1>");
        out.print("        <hr >");
        out.print("        <table border='1px' align='center' width='50%'>");
        out.print("            <tr>");
        out.print("                <th>序号</th>");
        out.print("                <th>部门编号</th>");
        out.print("                <th>部门名称</th>");
        out.print("                <th>操作</th>");
        out.print("            </tr>");
        /*以上是死的*/

        //连接数据库，查询所有的部门
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //注册驱动与获取连接
            conn = DButil.gerConnection();
            //获取预编译的数据库操作对象
            String sql = "select deptno as a,dname,loc from dept";
            ps = conn.prepareStatement(sql);
            //执行sql语句
            rs = ps.executeQuery();
            //处理结果集
            while(rs.next())
            {
                int i = 0;
                String deptno = rs.getString("a");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                out.print("            <tr>");
                out.print("                <td>"+(++i)+"</td>");
                out.print("                <td>"+deptno+"</td>");
                out.print("                <td>"+dname+"</td>");
                out.print("                <td>");
                out.print("                    <a href='javascript:void(0)' onclick='del("+deptno+")'>删除</a>");
                out.print("                    <a href='"+contextPath+"/dept/edit?deptno="+deptno+"'>修改</a>");
                out.print("                    <a href='"+contextPath+"/dept/detail?deptno="+deptno+"'>详情</a>");
                out.print("                </td>");
                out.print("            </tr>");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            //释放资源
            DButil.close(conn,ps,rs);
        }


        /*以下是死的*/
        out.print("        </table>");
        out.print("        <hr >");
        out.print("        <a href='/oa/add.html'>新增部门</a>");
        out.print("    </body>");
        out.print("</html>");
    }
}
