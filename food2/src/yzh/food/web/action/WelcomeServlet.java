package yzh.food.web.action;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import yzh.food.utils.DButil;
import yzh.food.web.bean.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取cookie
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if(name.equals("username"))
                {
                    username = cookie.getValue();
                }else if (name.equals("password"))
                {
                    password = cookie.getValue();
                }
            }
        }

        //要在这里使用username和password变量
        if(username != null && password != null)
        {
            //验证用户名和密码是否正确
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            boolean success = false;
            try {
                conn = DButil.gerConnection();
                String sql = "select * from t_user where username = ? and password = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1,username);
                ps.setString(2,password);
                rs = ps.executeQuery();
                if(rs.next())
                {
                    //登陆成功
                    success = true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                DButil.close(conn,ps,rs);
            }
            if(success)
            {
                //获取session
                HttpSession session = request.getSession();
                //session.setAttribute("username",username);
                User user = new User(username,password);
                session.setAttribute("user",user);

                //正确，登陆成功，跳转到部门列表页面
                response.sendRedirect(request.getContextPath() + "/dish/list");
            }else
            {
                //错误，登陆失败，跳转到登陆页面
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }else
        {
            //跳转到登陆页面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
