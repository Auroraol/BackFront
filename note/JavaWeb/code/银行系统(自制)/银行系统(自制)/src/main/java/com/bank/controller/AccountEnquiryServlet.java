package com.bank.controller;

import com.bank.service.AccountEnquiryService;
import com.bank.service.AccountService;
import com.bank.service.impl.AccountEnquiryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author: LFJ
 * @Date: 2023-07-16 18:54
 */

@WebServlet(name = "AccountEnquiryServlet", value = "/accountEnquiry")   //http://localhost:8080/netBank/accountEnquiry
public class AccountEnquiryServlet extends HttpServlet {

	private AccountEnquiryService qccountEnquiryService =  new AccountEnquiryServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String method = req.getParameter("method");
		switch (method) {
			case "list":
				try {
					req.setAttribute("list", this.qccountEnquiryService.list().getList());
					req.getRequestDispatcher("admin/adminmanager.jsp").forward(req, resp);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
				break;
			case "search":
				//搜索功能
//				resp.sendRedirect("/dormitoryAdmin?method=list");
				break;
			case "save":
				//保存
//				resp.sendRedirect("/dormitoryAdmin?method=list");
				break;
			case "update":
				//添加数据
//				resp.sendRedirect("/dormitoryAdmin?method=list");
				break;
			case "delete":
				//删除
//				resp.sendRedirect("/dormitoryAdmin?method=list");
				break;
			case "add":
				//开户
				req.getRequestDispatcher("admin/add.jsp").forward(req, resp);
				break;
		}
	}
}
