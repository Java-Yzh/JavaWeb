package com.powernode.bank.web;

import com.powernode.bank.exceptions.MoneyNotEnoughException;
import com.powernode.bank.service.AccountService;
import com.powernode.bank.service.impl.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 账户小程序
 * AccountServlet是一个司令官。他负责调度其他组件来完成任务。
 * @author 老杜
 * @version 2.0
 * @since 2.0
 */
@WebServlet("/transfer")
public class AccountServlet extends HttpServlet { // AccountServlet作为Controller

    private AccountService accountService = (AccountService) new AccountServiceImpl(); // 多态

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 接收数据
        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        double money = Double.parseDouble(request.getParameter("money"));
        try {
            // 调用业务方法处理业务（调度Model处理业务）
            accountService.transfer(fromActno, toActno, money);
            // 执行到这里了，说明成功了。
            // 展示处理结果（调度View做页面展示）
            response.sendRedirect(request.getContextPath() + "/success.jsp");
        } catch(MoneyNotEnoughException e) {
            // 执行到这里了，说明失败了。（余额不足）
            // 展示处理结果（调度View做页面展示）
            response.sendRedirect(request.getContextPath() + "/moneynotenough.jsp");
        } catch(Exception e){
            // 执行到这里了，说明失败了。
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
