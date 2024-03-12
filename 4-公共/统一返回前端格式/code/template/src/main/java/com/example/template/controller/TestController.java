package com.example.template.controller;

import com.example.template.common.vo.ResponseResult;
import com.example.template.entity.User;
import com.example.template.enums.ResponseCodeEnum;
import com.example.template.exception.BizException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LFJ
 * @Date: 2024-01-25 11:05
 */
@RestController

public class TestController {
	@GetMapping("/test1")
	public Map<String, Object> test1(){
		Map<String, Object> data = new HashMap<>();
		data.put("name", "白");
		data.put("sex", "男");

		return data;
	}
	/*
	// 不使用ResponseBodyAdvice进行统一响应格式封装, 可以使用以下方式
	@GetMapping("/test1")
	public Map<String, Object> test1(){
		Map<String, Object> data1 = new HashMap<>();
		data.put("name", "白");
		data.put("sex", "男");

		return ResponseResult.success(data1);
	}
	* */
	@GetMapping("/test2")
	public User test2(){
		User user = new User();
		user.setId(100);
		user.setName("jindao");
		return user;
	}


	/**
	 * 异常处理
	 */

	// 方式1: 直接返回错误
	@GetMapping("/test3")
	public ResponseResult test3(){
		return ResponseResult.accountAlreadyExists();
	}

	// 方式2: 进行统一异常处理封装, 通过throw new xxx进行调用
	@GetMapping("/test4")
	public Map<String, Object> test4(){
		Map<String, Object> data = new HashMap<>();
		data.put("name", "白");
		data.put("sex", "男");

		int a = 1;
		if (a==1) {
			throw new BizException(ResponseCodeEnum.ACCOUNT_LOCK);
		}
		return data;
	}

}
