package com.bjpowernode.oa.web.action;

import com.bjpowernode.oa.bean.Dept;
import com.bjpowernode.oa.utils.DButil;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequestWrapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/dept/list","/dept/detail","/dept/delete","/dept/save","/dept/modify"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();
        if (servletPath.equals("/dept/list")) {
            dolist(request, response);
        } else if (servletPath.equals("/dept/detail")) {
            dodetail(request, response);
        } else if (servletPath.equals("/dept/delete")) {
            dodelete(request, response);
        } else if (servletPath.equals("/dept/save")) {
            dosave(request, response);
        } else if (servletPath.equals("/dept/modify")) {
            domodify(request, response);
        }

    }

    private void domodify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //获取部门信息
        //解决乱码问题
        request.setCharacterEncoding("UTF-8");
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        int count = 0;
        //连接数据库执行update语句
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DButil.gerConnection();
            String sql = "update dept set dname = ?, loc = ? where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,loc);
            ps.setString(3,deptno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,null);
        }
        if(count == 1)
        {
            //request.getRequestDispatcher("/dept/list").forward(request,response);
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }
    }

    private void dosave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
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
    }

    private void dodelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //获取部门编号
        String dno = request.getParameter("deptno");
        Connection conn = null;
        PreparedStatement ps = null;
        Dept dept = new Dept();
        int count = 0;
        try {
            conn = DButil.gerConnection();
            String sql = "delete from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dno);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,null);
        }

        //重定向到列表页面
        if (count == 1)
        {
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath+"/dept/list");
        }

    }

    //连接数据库展示部门详情
    private void dodetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //获取部门编号
        String dno = request.getParameter("dno");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Dept dept = new Dept();
        try {
            conn = DButil.gerConnection();
            String sql = "select dname, loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dno);
            rs = ps.executeQuery();
            if(rs.next())
            {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                //封装对象，（豆子）
                dept.setLoc(loc);
                dept.setDname(dname);
                dept.setDeptno(dno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,rs);
        }

        //这个豆子只有一个，所以不需要袋子，只需要将这个咖啡豆放到request域中即可
        request.setAttribute("dept",dept);

//        String f = request.getParameter("f");
//        if(f.equals("d"))
//        {
//            //转发
//            request.getRequestDispatcher("/detail.jsp").forward(request,response);
//        }else if (f.equals("m"))
//        {
//            request.getRequestDispatcher("/edit.jsp").forward(request,response);
//        }
        request.getRequestDispatcher("/" + request.getParameter("f") + ".jsp").forward(request,response);
    }

    //连接数据库，查询所有的部门信息，将所有的部门信息收集好，然后跳转到jsp做页面展示
    private void dolist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //准备一个容器，用来专门存储部门
        List<Dept> depts = new ArrayList();

        //链接数据库获取数据
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DButil.gerConnection();
            String sql = "select deptno,dname,loc from dept";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                //将以上的零散数据封装为Java对象
                Dept dept = new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);

                //将部门对象放进集合中
                depts.add(dept);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,rs);
        }

        //将一个集合放进请求域中
        request.setAttribute("deptlist",depts);

        //转发(不用重定向，需保证一次请求)
        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }
}
