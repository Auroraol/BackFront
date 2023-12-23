package com.jindao.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @Author: LFJ
 * @Date: 2023-03-22 10:13
 */
public class ImageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 让浏览器3秒刷新一次
		resp.setHeader("refresh", "3");
		// 在内存中创建一个图片
		BufferedImage image = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);// 宽、高、颜色
		// 得到图片
		Graphics2D g = (Graphics2D) image.getGraphics();// 得到一只2D的笔
		// 设置图片的背景颜色
		g.setColor(Color.white);
		g.fillRect(0, 0, 80, 20);// 填充颜色
		// 换个背景颜色
		g.setColor(Color.BLUE);
		// 设置字体样式：粗体，20
		g.setFont(new Font(null,Font.BOLD,20));
		// 画一个字符串（给图片写数据）
		g.drawString(makeNum(),0,20);

		// 告诉浏览器，这个请求用图片的方式打开
		resp.setContentType("image/jpeg");
		// 网站存在缓存，不让浏览器缓存
		resp.setDateHeader("expires",-1);
		resp.setHeader("Cache-Control","no-cache");
		resp.setHeader("Pragma","no-cache");

		// 把图片写给浏览器
		boolean write = ImageIO.write(image, "jpg", resp.getOutputStream());
	}

	// 生成随机数
	private String makeNum() {
		Random random = new Random();
		String num = random.nextInt(9999999) + "";// 随机数，最大七位，[0,9999999)
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 7 - num.length(); i++) {// 不足七位，则添加0
			sb.append("0");
		}
		num = sb.toString()+num;// 不足七位，在随机数前面添加0
		return num;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
