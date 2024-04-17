package oa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//模板类
@WebServlet(value = {"/dept/list", "/dept/save", "/dept/edit", "/dept/detail", "/dept/delete", "/dept/modify"})
//模糊匹配
//@WebServlet("/dept/*")
public class DeptServlet extends HttpServlet {
    @Override
    //模板方法
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if("/dept/list".equals(servletPath))
        {
            dolist(request,response);
        } else if ("/dept/save".equals(servletPath)) {
            dosave(request,response);
        }else if ("/dept/edit".equals(servletPath)) {
            doedit(request,response);
        }else if ("/dept/detail".equals(servletPath)) {
            dodetail(request,response);
        }else if ("/dept/delete".equals(servletPath)) {
            dodelete(request,response);
        }else if ("/dept/modify".equals(servletPath)) {
            domodify(request,response);
        }
    }

    private void domodify(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
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
        else {
            //request.getRequestDispatcher("error.html").forward(request, response);
            response.setContentType(request.getContentType() + "error.html");
        }
    }

    private void dodelete(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        String deptno = request.getParameter("deptno");
        //连接数据库删除数据
        Connection conn = null;
        PreparedStatement ps = null;
        int count;
        try {

            conn = DButil.gerConnection();
            conn.setAutoCommit(false);
            String sql = "delete from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            count = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if(conn != null)
            {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,null);
        }
        if(count == 1)
        {
            //删除成功
            //仍然跳转到部门列表页面
            //部门列表页面需要连接数据库，到servlet，转发
            //request.getRequestDispatcher("/dept/list").forward(request,response);
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }
        else
        {
            //删除失败
            //request.getRequestDispatcher("/error.html").forward(request,response);
            response.sendRedirect(request.getContextPath() + "/error.html");
        }
    }


    private void dodetail(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
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


    private void doedit(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
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

    private void dosave(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
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

    private void dolist(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
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
