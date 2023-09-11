package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service  //自动注册bean  // 不加它就用bean认为加
// @Component   和 Service 没有啥区别,  Component是比较通用的东西
public class AdminServiceImpl implements UserService {
	@Override
	public User show() {
		User user = new User(1, "AdminServiceImpl", "男" , 12);
		return user;
	}

	@Override
	public User get(int id) {
		User user = new User(id, "AdminServiceImpl", "男" , 12);
		return user;
	}

}
