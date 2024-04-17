package com.bjpowernode.oa;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeptSaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取部门信息
        //解决乱码问题
        request.setCharacterEncoding("UTF-8");
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        int count;
        //连接数据库执行insert语句
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DButil.gerConnection();
            String sql = "insert into dept(deptno,dname,loc) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            ps.setString(2,dname);
            ps.setString(3,loc);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,null);
        }
        if(count == 1)
        {
            //转发是一次请求，在这块用的是dopost请求，转过去也会进行dopost，如果没有dopost方法，会出现405错误

            //request.getRequestDispatcher("/dept/list").forward(request,response);

            //这里最好使用重定向（浏览器会发一次全新的请求。）
            //浏览器在地址栏上发请求，是get请求
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }
        else {
            //request.getRequestDispatcher("error.html").forward(request, response);
            response.sendRedirect(request.getContextPath() + "/error.html");
        }
    }
}
