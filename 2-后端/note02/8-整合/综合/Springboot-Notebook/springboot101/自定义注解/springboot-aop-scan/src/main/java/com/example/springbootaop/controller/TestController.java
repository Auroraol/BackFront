package com.example.springbootaop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 20:33
 */
@Controller
public class TestController {
	@GetMapping("index")
	@ResponseBody
	public String index(){
		return "Hello";
	}

	// com.example.springbootaop.aspect.WebLogAspect配置了controller总以xxxTicket结尾的方法
	@GetMapping("world")
	@ResponseBody
	public String indexTicket(){
		return "world";
	}
}
