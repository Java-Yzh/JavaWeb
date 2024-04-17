package com.bjpowernode.oa.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        /**
         * 什么情况下不能拦截
         *      目前写的路径是：/* 表示所有的路径拦截
         *
         *      用户访问 index.jsp 时不能拦截
         *      用户已将登陆了，需要放行，不能拦截
         *      用户要去登陆，这个也不能拦截
         *      WelcomeServlet也不能拦截
         * */

        String servletPath = request.getServletPath();
        //获取session（不需要新建）
        HttpSession session = request.getSession(false);
//        if(("/index.jsp".equals(servletPath)) || ("/welcome".equals(servletPath)) ||
//                ("/user/login".equals(servletPath)) || ("/user/exit".equals(servletPath)) ||
//                (session != null && session.getAttribute("username") != null)) {
        if(("/index.jsp".equals(servletPath)) || ("/welcome".equals(servletPath)) ||
                ("/user/login".equals(servletPath)) || ("/user/exit".equals(servletPath)) ||
                (session != null && session.getAttribute("user") != null)) {
            //继续往下走
            filterChain.doFilter(request,response);
        }else {
            //未登录则跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
