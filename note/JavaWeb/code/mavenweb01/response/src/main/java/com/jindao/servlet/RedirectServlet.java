package com.jindao.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LFJ
 * @Date: 2023-03-22 10:42
 */
public class RedirectServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    /*resp.setHeader("Location","/response_war/image");
        resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);*/
		resp.sendRedirect("/r/img"); // 重定向相当于上面两行代码  //参数:  虚拟访问路径/url请求路径
	}
}
