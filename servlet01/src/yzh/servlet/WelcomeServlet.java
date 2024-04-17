package yzh.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(urlPatterns = "welcome")
//这个value属性和urlPatterns属性一样，都是用来指定Servlet的映射路径的。
//如果注解的属性名是value ，属性名也可以省略不写
//@WebServlet(value = "/welcome")
@WebServlet("/welcome")
public class WelcomeServlet extends HelloServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print("欢迎学习servlet");
    }
}
