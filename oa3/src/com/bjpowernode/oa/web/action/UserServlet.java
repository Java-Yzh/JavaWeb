package com.bjpowernode.oa.web.action;

import com.bjpowernode.oa.bean.User;
import com.bjpowernode.oa.utils.DButil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/user/login","/user/exit"})
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/user/login"))
        {
            doLogin(request,response);
        }else if (request.getServletPath().equals("/user/exit"))
        {
            doExit(request,response);
        }
    }

    private void doExit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{


        //获取session对象，销毁它
        HttpSession session = request.getSession(false);
        if(session != null)
        {
            //从session域中删除user对象
            session.removeAttribute("user");

            //手动销毁session对象
            session.invalidate();
            //销毁cookie(退出系统消除所有cookie)
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("username") || cookie.getName().equals("password"))
                    {
                        //设置cookie的有效期为0，表示删掉cookie
                        cookie.setMaxAge(0);
                        //设置cookie关联路径
                        cookie.setPath(request.getContextPath());//删除cookie时注意路径问题，必须完全相同
                        //响应cookie给浏览器，浏览器会将之前的cookie给覆盖
                        response.addCookie(cookie);
                    }
                }
            }
            //跳转到登陆页面
            response.sendRedirect(request.getContextPath());
        }

    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean success = false;
        //你要做一件什么事情？验证用户名和密码是否正确
        //获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //连接数据库进行验证
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DButil.gerConnection();
            String sql = "select * from t_user where username = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            //如果查询成功，这个结果集中最多只有一条数据
            if(rs.next())
            {
                success = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,rs);
        }

        //登陆成功/失败
        if(success)
        {
            //获取session对象(这里的要求是必须获取到session，没有session也要获取到session)
            HttpSession session = request.getSession();
            User user = new User(username,password);
            //session.setAttribute("username",username);

            session.setAttribute("user",user);

            //登陆成功了，并且用户确实选择了十天内免登录功能
            String f = request.getParameter("f");
            if("1".equals(f))
            {
                //创建cookie对象存储用户名
                Cookie cookie1 = new Cookie("username",username);
                //创建cookie对象存储密码
                Cookie cookie2 = new Cookie("password",password);
                //设置cookie的有效期为10天
                cookie1.setMaxAge(60 * 60 * 24 * 10);
                cookie2.setMaxAge(60 * 60 * 24 * 10);
                //设置cookie的有效路径，（只要访问这个应用，浏览器就一点携带这两个cookie）
                cookie1.setPath(request.getContextPath());
                cookie2.setPath(request.getContextPath());
                //响应cookie给浏览器
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }
            //成功，跳转到用户列表页面(重定向)
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }else {
            //失败，跳转到失败页面
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
