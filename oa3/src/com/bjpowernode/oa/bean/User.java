package com.bjpowernode.oa.bean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

public class User implements HttpSessionBindingListener {
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        //用户登陆了
        //user类型的对象向session中添加了
        //获取ServletContext对象
        ServletContext application = event.getSession().getServletContext();
        //获取在线人数
        Object onlinecount = application.getAttribute("onlinecount");
        if (onlinecount == null) {
            application.setAttribute("onlinecount",1);
        }else
        {
            int count = (Integer)(onlinecount);
            count++;
            application.setAttribute("onlinecount",count);
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        //用户退出了
        //user类型的数据从session域中删除了
        //获取ServletContext对象
        ServletContext application = event.getSession().getServletContext();
        Object onlinecount = application.getAttribute("onlinecount");
        int count = (Integer)(onlinecount);
        count--;
        application.setAttribute("onlinecount",count);
    }

    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
