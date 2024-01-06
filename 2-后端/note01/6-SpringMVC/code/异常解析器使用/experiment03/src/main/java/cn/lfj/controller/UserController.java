package cn.lfj.controller;

import cn.lfj.exception.LoginException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: LFJ
 * @Date: 2023-10-18 21:41
 */

@Controller
@RequestMapping("/user")
public class UserController {

	// 抛出异常
	/*
	 * http://localhost:8080//user/test1
	 * 抛出异常: 跳转到http://localhost:8080/user/global
	 */
	@RequestMapping("/test1")
	public String test1() {
		if (1 == 1)
			throw new LoginException("test01~~");
		return "index";
	}

	//
	@RequestMapping("/login")
	public String login_page() {
		System.out.println("登录页面");
		return "login";
	}

	//http://localhost:8080//user/perm
	@RequestMapping("/perm")
	public String perm_error() {
		System.out.println("权限不足页面");
		return "perm_error";
	}

	@RequestMapping("/global")
	public String global_error() {
		System.out.println("全局错误页面");
		return "global_error";
	}
}