package com.example.zengtengpengtest.controller;
import com.example.zengtengpengtest.entity.ZUser;
import com.zengtengpeng.operation.RedissonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: LFJ
 * @Date: 2024-01-23 14:48
 */

@Controller
@RequestMapping("/object")
public class ObjectController {

	@Autowired
	private RedissonObject redissonObject;

	@GetMapping("/index")
	@ResponseBody
	public String index(){
		return "ok";
	}

	/**
	 * 设置值
//	 * @param user
//	 * @param request
//	 * @param response
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping("/object1")
	@ResponseBody
	public String object1() {
	//public String object1(ZUser user, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
		//RedissonObject redissonObject = new RedissonObject();
		ZUser user1 = new ZUser();
		user1.setName("test");
		user1.setAge("123");
		redissonObject.setValue("object1", user1,-1L);

		return "";
	}

	/**
	 * 获取值
//	 * @param user
//	 * @param request
//	 * @param response
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping("/object2")
	@ResponseBody
	//public Object object2(ZUser user, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
	public Object object2() throws InterruptedException {
		//如果缓存有值从缓存里面读, 否则从接口函数读实时数据存入redis
//		redissonObject.getValue("object1",()->"获取值逻辑",200213213L);
		//RedissonObject redissonObject = new RedissonObject();
		return redissonObject.getValue("object1");
	}

	/**
//	 * 如果对象不存在则设置,否则不设置
//	 * @param user
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws InterruptedException
//	 */
//	@RequestMapping("/object3")
//	@ResponseBody
//	public String object3(User user, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
//		return redissonObject.trySetValue("object1","object1-2")+"";
//	}
}