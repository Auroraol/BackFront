package com.bank.controller;

import com.bank.service.AccountService;
import com.bank.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author: LFJ
 * @Date: 2023-07-16 16:15
 */


@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

	private AccountService accountService = new AccountServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("userName");
		String password = req.getParameter("passWord");

		try {
				Integer i = 	accountService.register(name, password);
				if (i == -1) {
					req.setAttribute("failMsg", "  注册失败");  //存
					req.getRequestDispatcher("login.jsp").forward(req, resp);  //回到登录页面
				}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
