package com.bjpowernode.oa.utils;

import java.sql.*;
import java.util.ResourceBundle;

public class DButil {

    //静态变量
    //从上到下顺序执行
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources.jdbc");
    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String user = bundle.getString("user");
    private static String password = bundle.getString("password");

    static {
        //注册驱动(注册驱动只需要注册一次，放在静态代码块中，DBUtil类加载时执行)
        try {
            //“com.mysql.jdbc.Driver”是连接数据库的驱动，不能写死，因为以后可能连接其他的数据库
            //如果连接数据库oracle时，还需要修改java代码，显然违背了ocp原则
            //ocp开闭原则：对扩展开放，对修改关闭。（什么是符合ocp？在进行功能扩展时，不需要修改Java代码）
            Class.forName(driver);
        }catch (ClassNotFoundException c)
        {
            c.printStackTrace();
        }
    }

    //获取数据库连接
    public static Connection gerConnection() throws SQLException {
        //获取连接
        Connection conn = DriverManager.getConnection(url,user,password);
        return conn;
    }

    //释放资源
    public static void close(Connection conn, Statement ps, ResultSet rs)
    {
        try {
            if(rs != null)
            {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(ps != null)
            {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null)
            {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
