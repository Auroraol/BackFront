package com.example.formtest.controller;
import com.example.formtest.dao.User;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LFJ
 * @Date: 2023-12-27 17:41
 */
@Controller
public class FieldController {

	// 默认页面
	@RequestMapping("/toLogin")
	public String toLogin(Model model){
		model.addAttribute("user",new User());
		return "loginPage";
	}

	// 登录页面
	@ResponseBody
	@RequestMapping("/login")
//	public String login(@RequestBody User user){  // 报错
	public User login(@ModelAttribute User user){
		System.out.println("==========用户名: "+user.getUserName());
		System.out.println("==========密码:   "+user.getUserPassword());
		System.out.println("生日1是几号: " + user.getBirthDate1().format(DateTimeFormatter.ofPattern("dd")));
		System.out.println("==========生日2:   "+user.getBirthDate2());
		return user;
	}
}

