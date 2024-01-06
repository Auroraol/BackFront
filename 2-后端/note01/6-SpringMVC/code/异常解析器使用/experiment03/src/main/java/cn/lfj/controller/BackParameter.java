package cn.lfj.controller;

import cn.lfj.entity.People;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author: LFJ
 * @Date: 2023-10-18 17:01
 */

@Controller
@RequestMapping("/BackParameter")
public class BackParameter {

	// 转发
	/**
	 * http://localhost:8080/BackParameter/test1
	 * param: id name gender birth
	 **/
	@RequestMapping("/test1")
	public String testParam1(Model model){
		String msg = "我是---request---forward:/BackParameter.jsp";
		model.addAttribute("msg", msg);
		// return "hello";
		return "forward:/BackParameter.jsp";
	}

	// 重定向
	/**
	 * http://localhost:8080/BackParameter/test2
	 * 重定向到: http://localhost:8080/BackParameter.jsp
	 **/
	@RequestMapping("/test2")
	public String providerInfo22(HttpSession session) {
		String msg = "我是---session---redirect:/BackParameter.jsp";
		session.setAttribute("msg", msg);
		//return new ModelAndView("redirect:/hello");
		return "redirect:/BackParameter.jsp";
	}

	// @RequestBody
	/**
	 * http://localhost:8080/BackParameter/test3
	 */
	@RequestMapping("/test3")
	@ResponseBody
	public People testParam3() {
		People people = new People();
		people.setCount(100);
		people.setPeoplename("张刚");
		return people;
	}
}
