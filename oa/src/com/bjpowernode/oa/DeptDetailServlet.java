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
import java.util.zip.CheckedOutputStream;

public class DeptDetailServlet extends HttpServlet {
    @Override
    //在doget方法连接数据库，根据部门编号查看详情
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取部门编号
        String deptno = request.getParameter("deptno");

        //设置响应的内容类型以及字符集。防止中文乱码问题。
        response.setContentType("text/html;charset = UTF-8");
        //获取打印对象
        PrintWriter out = response.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("   <head>");
        out.print("       <meta charset='utf-8'>");
        out.print("       <title>部门详情</title>");
        out.print("   </head>");
        out.print("   <body>");
        out.print("       <h1>部门详情</h1>");
        out.print("       <hr >");



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
                out.print("               部门编号："+deptno+" <br>");
                out.print("               部门名称："+dname+"<br>");
                out.print("               部门位置："+loc+"<br>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,rs);
        }
        out.print("       <input type='button' value='后退' onclick='window.history.back()'/>");
        out.print("   </body>");
        out.print("</html>");
    }
}
