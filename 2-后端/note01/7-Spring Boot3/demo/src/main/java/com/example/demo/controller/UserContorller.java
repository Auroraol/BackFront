package com.example.demo.controller;

import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
*    测试  RestController
 */

@Slf4j
@RestController   // 返回对象
@RequestMapping("/rest")
public class UserContorller {

	@GetMapping("/show")
	public User show(){
		User user = new User(1, "张三", "男" , 12);
		return user;
	}

	@GetMapping("/{id}")
	public User get(@PathVariable("id") int id){
		User user = new User(id, "张三", "男" , 12);
		return user;
	}

	/*
	* 新增数据
	 * 假设前端发送数据  前端发过来的数据用RequestBody修饰
	 */
	@PostMapping
	public User addUser(@RequestBody User user){
		return user;
	}
}
