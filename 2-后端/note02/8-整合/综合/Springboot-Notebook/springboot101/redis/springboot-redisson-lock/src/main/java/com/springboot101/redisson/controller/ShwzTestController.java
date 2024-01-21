package com.springboot101.redisson.controller;

import com.springboot101.redisson.service.ShwzTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LFJ
 * @Date: 2024-01-21 15:10
 */

@Controller
public class ShwzTestController {

	@Autowired
	ShwzTestService shwzTestService;

	@ResponseBody
	@GetMapping("test")
	public String createOrderTest() throws InterruptedException {
		return shwzTestService.decreasePrice("1234-1234-1234", 1);
	}
}
