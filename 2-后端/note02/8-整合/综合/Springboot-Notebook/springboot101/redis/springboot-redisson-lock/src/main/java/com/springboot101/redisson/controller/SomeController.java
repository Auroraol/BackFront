package com.springboot101.redisson.controller;

import com.springboot101.redisson.service.DistributedLockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LFJ
 * @Date: 2024-01-21 15:01
 */
@RestController
public class SomeController {

	@Autowired
	private DistributedLockServer distributedLockServer;

	@GetMapping("/doSomething")
	public String doSomething() {
		distributedLockServer.doSomethingWithLock();
		return "Do something with lock successfully!";
	}
}
