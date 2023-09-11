package com.jindao.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * @Author: LFJ
 * @Date: 2023-03-22 9:31
 */
public class FileServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 要获取下载文件的路径
		//String realPath = this.getServletContext().getRealPath("/1.png");
		String realPath = "C:\\Users\\16658\\Desktop\\java\\java\\note\\JavaWeb\\code\\mavenweb01\\response\\src\\main\\resources\\1.png";
		System.out.println("下载文件的路径: " + realPath);

//		2. 下载的文件名
		String filename = realPath.substring(realPath.lastIndexOf("\\") + 1);// 1.png
//		3. 设置想办法训浏览器能够支持下载我们需要的东西
		resp.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(filename,"utf-8"));
//		4. 获取下载文件的输入流
		FileInputStream in = new FileInputStream(realPath);
//		5. 创建缓冲区
		int len = 0;
		byte[] buffer = new byte[1024];// 每次读取的长度
//		6. 获取OutputStream对象
		ServletOutputStream out = resp.getOutputStream();
//		7.将FileoutputStream流写入到buffer缓冲区
		while ((len = in.read(buffer))>0){// 每次读取的长度大于0的情况下，就写出去
			out.write(buffer,0,len);// 写出字节，从0写到len
		}
		// 8.使用OutputStream将缓冲区中的数据输出到客户端！
		in.close();
		out.close();
	}
}
