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
 * @Date: 2023-03-23 10:22
 */
public class sessionDemo01 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-16");
		resp.setCharacterEncoding("utf-16");
		// 得到session数据
		HttpSession session = req.getSession();
		// 给session中存入东西(可以是对象)
		session.setAttribute("name", new Person("小明", 18));
		//获得session的id
		String id = session.getId();
		// 判断session是不是新的创建
		if (session.isNew()){
			resp.getWriter().write("session创建成功, ID" + id);

		}else {
			resp.getWriter().write("session以及在服务器中存在了, ID" + id);
		}
	}
}
