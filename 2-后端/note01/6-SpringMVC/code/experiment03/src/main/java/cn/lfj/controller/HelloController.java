package cn.lfj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: LFJ
 * @Date: 2023-10-15 16:13
 */

@Controller
@RequestMapping("/hello")  //访问路径 ，等价于url-pattern
public class HelloController {
	@RequestMapping("/test1")  //访问路径
	public String hello1(){
		System.out.println("hello world1");
		return "index"; // 跳转:/index.jsp
	}
	@RequestMapping("/test2") //访问路径
	public String hello2(){
		System.out.println("hello user2");
		return "views/user";//  跳转:/views/user.jsp
	}

	@RequestMapping("") //访问路径
	public String hello3(){
		System.out.println("hello user3");
		return "views/user";//  跳转:/views/user.jsp
	}
}