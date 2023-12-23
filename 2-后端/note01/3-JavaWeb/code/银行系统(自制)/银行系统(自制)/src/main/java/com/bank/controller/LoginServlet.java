package com.bank.controller;

import com.bank.dto.AccountDto;
import com.bank.model.Account;
import com.bank.service.AccountService;
import com.bank.service.impl.AccountServiceImpl;
import com.bank.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/account")
public class LoginServlet extends HttpServlet {

    private AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");
        switch (method) {
            case "login":
                AccountDto accountDto = null;
                // dopost 得到前端数据
                String name = req.getParameter("userName");
                String password = req.getParameter("passWord");
                String type = req.getParameter("type");
                switch (type) {
                    case "admin":
                        try {
                            accountDto = this.accountService.adminLogin(name, password);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "user":
                        try {
                            accountDto = this.accountService.userLogin(name, password);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
                switch (accountDto.getCode()) {
                    case -1:
                        req.setAttribute("failMsg", "  用户名不存在");  //存
                        //                req.setAttribute("userNameError", "  用户名不存在");  //存
                        req.getRequestDispatcher("login.jsp").forward(req, resp);  //回到登录页面
                        break;
                    case -2:
                        req.setAttribute("failMsg", "  密码错误");
                        //                req.setAttribute("passWordError", "  密码错误");
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                        break;
                    case 1:
                        //重定向,跳转到登录成功管理员界面
                        HttpSession session = req.getSession();
                        session.setAttribute( "admin", accountDto.getAdminAccount());  // session保存数据
                        resp.sendRedirect( "/netBank/admin/systemadmin.jsp");
                        break;
                    case 0:
                        //跳转到用户界面
                        HttpSession session2 = req.getSession();
                        session2.setAttribute( "user", accountDto.getAccount());  // session保存数据,网页能拿到里面的东西
                        resp.sendRedirect( "/netBank/index.jsp");
                        break;
                }
                break;
            case "logout":
                req.getSession().invalidate();  //session清除
                resp.sendRedirect("/netBank/login.jsp");
                break;
        }
    }
}
