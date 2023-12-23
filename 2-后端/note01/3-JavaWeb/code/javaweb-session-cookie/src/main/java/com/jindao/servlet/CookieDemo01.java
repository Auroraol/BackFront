package com.jindao.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

/**
 * @Author: LFJ
 * @Date: 2023-03-22 16:35
 */
public class CookieDemo01 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-16");
		resp.setCharacterEncoding("utf-16");


		PrintWriter out = resp.getWriter();  //向浏览器写入一些东西
		Cookie[] cookies = req.getCookies();  //这星返回数纽，说明Cookie可能存在多个

		// 判断cookie是否
		if (cookies != null){
			out.write("你上一次访问的时间");
			for (Cookie cookie : cookies) {
				// 获取cookie名字
				if (cookie.getName().equals("lastLoginTime")) {
					//获取cookie值
					long lastLoginTime = Long.parseLong(cookie.getValue());
					Date date = new Date(lastLoginTime);
					out.write(date.toLocaleString());
				}
			}
		}else {
			// 第一次访问
			out.write("这是您第一次访问本网站");
		}


		// 服务器响应一个cookie
		Cookie cookie = new Cookie("lastLoginTime", System.currentTimeMillis() + "");
		cookie.setMaxAge(24*60*60);  // 设置cookie的有效期为一天，单位是：秒
		resp.addCookie(cookie);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
