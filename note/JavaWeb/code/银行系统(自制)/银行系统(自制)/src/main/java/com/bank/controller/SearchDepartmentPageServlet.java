//package com.bank.controller;
//
//import com.bank.dao.BankcardDao;
//import com.bank.dto.BankcardDto;
//import com.bank.model.Account;
//import com.bank.model.Bankcard;
//import com.bank.service.AccountEnquiryService;
//import com.bank.service.impl.AccountEnquiryServiceImpl;
//import com.bank.utils.PageUtil;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author: LFJ
// * @Date: 2023-07-17 15:14
// */
//
//@WebServlet(name = "AccountEnquiryServlet", value = "/accountEnquiry")
////@WebServlet(name = "SearchDepartmentPageServlet", value = "/SearchDepartmentPageServlet")
//public class SearchDepartmentPageServlet extends HttpServlet {
//	private BankcardDto bankcardDto = new BankcardDto();
//	private AccountEnquiryService departmentService = new AccountEnquiryServiceImpl();
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		this.doPost(req, resp);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		String method = req.getParameter("method");
//		switch (method) {
//			case "list":
//				// 从jsp页面接收到的参数
//				try {
//					int currentPage = Integer.parseInt(req.getParameter("currentPage"));
//				} catch (Exception e) {
//					return ;
//				}
//				try {
//					bankcardDto = departmentService.bankcardSize();
//				} catch (SQLException e) {
//					throw new RuntimeException(e);
//				}
//				// 创建页面划分对象
//				PageUtil pageUtil = new PageUtil(3, bankcardDto.getRecordCount(), 1);
//				// 查询到所有的部门信息
//				try {
//					bankcardDto = departmentService.bankcardInfo(pageUtil.getStart(), pageUtil.getEnd());
//				} catch (SQLException e) {
//					throw new RuntimeException(e);
//				}
//				// 创建一个部门集合，用来存放要显示的信息。
//				List<Bankcard> resultList = new ArrayList<>();
//				for (int i = pageUtil.getFromIndex(); i < pageUtil.getToIndex(); i++) {
//					resultList.add(bankcardDto.getList().get(i));
//				}
//
//				// 将部门和页面工具对象请求转发到指定的页面
//				req.setAttribute("list", resultList);
//				req.setAttribute("pageUtil", pageUtil);
//				req.getRequestDispatcher("admin/add.jsp").forward(req, response);
//				break;
//		}
//	}
//}
//
