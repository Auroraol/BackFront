package cn.lfj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: LFJ
 * @Date: 2023-10-15 16:24
 */

@Controller
public class AnnotationHandler {

	/**
	 * 业务方法
	 * 使用ModelAndView完成数据传递、视图解析
	 */
	@RequestMapping("/modelAndViewTest")
	public ModelAndView modelAndViewTest(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("name","Jack");
		modelAndView.setViewName("show");
		return modelAndView;
	}

	/**
	 * 业务方法
	 * 使用Model传值，String进行视图解析
	 */
	@RequestMapping("/modelTest")
	public String modelTest(Model model){
		model.addAttribute("name","Lucy");
		return "show" ;
	}

	/**
	 * 业务方法
	 * 使用Map传值，String解析视图
	 */
	@RequestMapping("/mapTest")
	public String mapTest(Map<String,String> map){
		map.put("name","Lilei");
		return "show";
	}

}