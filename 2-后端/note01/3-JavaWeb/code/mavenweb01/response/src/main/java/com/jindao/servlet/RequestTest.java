package com.jindao.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LFJ
 * @Date: 2023-03-22 11:13
 */
public class RequestTest extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 处理请求
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println(username  + ":" + password);

		//
		resp.sendRedirect("/r/success.jsp");
	}
}
