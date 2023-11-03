package com.jindao.request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Author: LFJ
 * @Date: 2023-03-22 15:47
 */
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String[] hobbys = req.getParameterValues("hobbys");

		System.out.println("=============");
		System.out.println(username);
		System.out.println(password);
		System.out.println(Arrays.toString(hobbys));

		// 转发 // 斜杠/就代表当前项目路径(虚拟路径)
		req.getRequestDispatcher("/success.jsp").forward(req, resp);

	}
}
