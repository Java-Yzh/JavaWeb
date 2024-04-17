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
import java.util.ArrayList;
import java.util.List;

import yzh.food.web.bean.Dish;

@WebServlet({"/dish/list","/dish/save","/dish/delete","/dish/edit","/dish/modify"})
public class DishServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if("/dish/list".equals(servletPath))
        {
            DoList(request,response);
        } else if ("/dish/save".equals(servletPath)) {
            DoSave(request,response);
        }else if("/dish/delete".equals(servletPath))
        {
            DoDelete(request,response);
        } else if ("/dish/edit".equals(servletPath)) {
            DoEdit(request,response);
        }else if ("/dish/modify".equals(servletPath))
        {
            DoModify(request,response);
        }

    }

    private void DoModify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取菜品信息
        String id = request.getParameter("id") ;
        String food = request.getParameter("name");
        //连接数据库修改数据
        Connection conn = null;
        PreparedStatement ps = null;
        int count;
        try {
            conn = DButil.gerConnection();
            String sql = "update food set food = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,food);
            ps.setString(2,id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,null);
        }

        if (count == 1)
        {
            response.sendRedirect(request.getContextPath() + "/dish/list");
        }

    }

    private void DoEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将菜品信息显示在修改页面
        //获取菜品编号
        String id = request.getParameter("id");
        //连接数据库查询出菜品信息
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Dish dish = new Dish();
        try {
            conn = DButil.gerConnection();
            String sql = "select id,food from food where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            rs = ps.executeQuery();
            if(rs.next())
            {
                int foodid = rs.getInt("id");
                dish.setId(foodid);
                String food = rs.getString("food");
                dish.setName(food);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,rs);
        }

        request.setAttribute("editdish",dish);
        request.getRequestDispatcher("/edit.jsp").forward(request,response);
    }

    private void DoDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int count;
        //获取要删除的菜品的id
        String id = request.getParameter("id");
        //连接数据库删除菜品数据
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DButil.gerConnection();
            String sql = "delete from food where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,null);
        }

        if (count == 1)
        {
            //重定向到菜品列表页面
            response.sendRedirect(request.getContextPath() + "/dish/list");
        }

    }

    private void DoSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取菜品信息
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String food = request.getParameter("food");
        int count;
        //连接数据库将菜品添加进去,执行insert语句
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DButil.gerConnection();
            String sql = "insert into food(id,food) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            ps.setString(2,food);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,null);
        }

        if (count == 1)
        {
            //重定向到列表页面
            response.sendRedirect(request.getContextPath() + "/dish/list");
        }

    }

    private void DoList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //准备一个容器，用来专门存储菜品
        List<Dish> dishs = new ArrayList();

        //连接数据库查出所有菜品
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DButil.gerConnection();
            String sql = "select id,food from food";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                String name = rs.getString("food");
                int id = rs.getInt("id");
                Dish food = new Dish();
                food.setName(name);
                food.setId(id);

                //将菜品集合放进集合里
                dishs.add(food);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DButil.close(conn,ps,rs);
        }

        //转发到列表页面
        request.setAttribute("dishs",dishs);

        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }
}
