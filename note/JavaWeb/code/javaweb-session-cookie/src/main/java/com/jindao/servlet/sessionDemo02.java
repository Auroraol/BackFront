package com.jindao.servlet;

import com.jindao.pojo.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: LFJ
 * @Date: 2023-03-23 10:35
 */
public class sessionDemo02 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-16");
		resp.setCharacterEncoding("utf-16");
		// 得到session数据
		HttpSession session = req.getSession();
		// 得到session中存入东西
		Person name = (Person) session.getAttribute("name");
		System.out.println(name);
	}
}
