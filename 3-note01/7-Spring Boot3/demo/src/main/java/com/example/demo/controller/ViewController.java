package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.lang.invoke.VarHandle;

@Controller      // 返回页面     //适配 服务端渲染   前后不分离模式
public class ViewController {

	/**
	 * 不带数据返回页面
	 */
	@RequestMapping ("/view")    //访问方式: (url:) http://localhost:8090/view
	public String view(){
		return "view";
	}

	/**
	 * 带数据返回页面(方法1)
	 */
	@GetMapping("/data1")
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView("view");
		String text = "<span style='color:red'>"+ "带数据返回页面(方法2)"+"</span>";
		modelAndView.addObject("msg", text);
		modelAndView.addObject("age", 18);

		return modelAndView;
	}

	/**
	 * 	 * 带数据返回页面(方法2)
	 */
	@GetMapping("/data2")
	public String hello(Model model){

		//把需要给页面共享的数据放到model中
		String text = "<span style='color:red'>"+ "带数据返回页面(方法2)"+"</span>";
		model.addAttribute("msg",text);
		model.addAttribute("age",18);

		return "view";
	}

}
