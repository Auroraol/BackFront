package com.jindao.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: LFJ
 * @Date: 2023-03-23 11:04
 */
public class sessionDemo03 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// 手动注销 (不用)
		session.removeAttribute("小明");
		session.invalidate();  // 注销-->相当于关闭浏览器

	}
}
