package yzh.food;

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
import java.util.Random;

public class suijiservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应的内容类型以及字符集。防止中文乱码问题。
        response.setContentType("text/html;charset = UTF-8");
        PrintWriter out = response.getWriter();
        //生成范围在1-15的随机正整数
        Random random = new Random();
        int id = random.nextInt(28) + 1;

        out.print("<!DOCTYPE html>");
        out.print("<html lang='en'>");
        out.print("<head>");
        out.print("<meta charset='UTF-8'>");
        out.print("<title>小豪推荐</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("<h1>下面是小豪推荐您的菜品哦</h1>");

        //连接数据库，展示随机菜品
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DButil.getConnection();
            String sql = "select food from food where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if (rs.next())
            {
                String food = rs.getString("food");
                out.print("<h2>"+food+"</h2>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,rs);
        }

        out.print("</body>");
        out.print("</html>");
    }
}
