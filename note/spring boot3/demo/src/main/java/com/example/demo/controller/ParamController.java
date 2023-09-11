package com.example.demo.controller;

import com.example.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 *  参数
 */

@Slf4j
@Controller
public class ParamController {


	/**
	 * 动态参数(RequestParam  带?) + 带数据返回页面(方法2)
	 */
	@GetMapping("/param")
	public String hello(@RequestParam(value = "age",   defaultValue = "0") int age,         //required 表示可以不传值
						@RequestParam(value = "name", required = false, defaultValue = "") String name,  //访问: http://localhost:8090/data2?age=参数?name=参数
						Model model){

		//把需要给页面共享的数据放到model中
		String text = "<span style='color:red'>"+name+"</span>";
		model.addAttribute("msg",text);
		model.addAttribute("age",18);

		return "view";
	}

	/**
	 * 动态参数 (PathVariable 不带?)   ps:看路径匹配章节
	 */
	@GetMapping("/a*/b?/{p1:[a-f]+}/**")
	public String hello(HttpServletRequest request, @PathVariable("p1") String path
			, Model model) {

		model.addAttribute("msg",path);
		return "view";
	}

	/**
	 * 在@Controller中希望返回json格式
	 * @return
	 */
	@GetMapping("/ReBody")
	@ResponseBody
	public User getUser(){
		User user = new User(1000, "张四", "男" , 12);
		//log. info("使用@GetMapping + @ResponseBody");
		return user;
	}
}
