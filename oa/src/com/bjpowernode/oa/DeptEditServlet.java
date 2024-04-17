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

public class DeptEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextpath = request.getContextPath();
        //获取部门编号
        String deptno = request.getParameter("deptno");

        //设置响应的内容类型以及字符集。防止中文乱码问题。
        response.setContentType("text/html;charset = UTF-8");
        //获取打印对象
        PrintWriter out = response.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("    <head>");
        out.print("        <meta charset='utf-8'>");
        out.print("        <title>修改部门</title>");
        out.print("    </head>");
        out.print("    <body>");
        out.print("        <h1>修改部门</h1>");
        out.print("        <hr >");
        out.print("        <form action='"+contextpath+"/dept/modify' method='post'>");

        //连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DButil.gerConnection();
            String sql = "select dname,loc from dept where deptno = ?";

            ps =  conn.prepareStatement(sql);
            ps.setString(1,deptno);
            rs = ps.executeQuery();
            if(rs.next())
            {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                out.print("                部门编号<input type='text' name='deptno' value='"+deptno+"' readonly /><br>");
                out.print("                部门名称<input type='text' name='dname' value='"+dname+"'/><br>");
                out.print("                部门位置<input type='text' name='loc' value='"+loc+"'/><br>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,rs);
        }
        out.print("            <input type='submit' value='修改'/><br>");
        out.print("        </form>");
        out.print("    </body>");
        out.print("</html>");
    }
}
