package yzh.food.web.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import yzh.food.utils.DButil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

@WebServlet({"/random"})
public class FoodServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if("/random".equals(servletPath))
        {
            DoRandom(request,response);
        }
    }

    private void DoRandom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //生成随机数用于生成随机菜品
        Random random = new Random();
        int id = random.nextInt(40) + 1;

        //连接数据库进行随机菜品展示
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String food = null;
        try {
            conn = DButil.gerConnection();
            String sql = "select food from food where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next())
            {
                food = rs.getString("food");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,rs);
        }
        //转发到随机菜品页面
        request.setAttribute("foodname",food);
        request.getRequestDispatcher("/random.jsp").forward(request,response);

    }
}
