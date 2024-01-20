package com.springboot101.limit.controller;

import com.springboot101.limit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LFJ
 * @Date: 2024-01-19 14:47
 * @Description:  演示方法
 */
@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;

	/**
	 * 用于在 Redis 中添加键值对
	 */
	@GetMapping("/order/add")
	public void addOrder() {
		orderService.addOrder();
	}

	/**
	 * 用于在 Redis 中查询addOrder()所添加的键值对
	 * @param keyId
	 * @return
	 */
	@GetMapping("/order/query")
	public String queryOrder(Integer keyId) {
		return orderService.getOrderById(keyId);
	}
}
