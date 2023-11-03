package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
* 用户服务 ----  核心逻辑
 */
@Slf4j
@Service  //注册bean
// @Component   和 Service 没有啥区别,  Component是比较通用的东西
public class UserServiceImpl implements UserService {
	@Override
	public User show() {
		User user = new User(1, "UserServiceImpl", "男" , 12);
		return user;
	}

	@Override
	public User get(int id) {
		User user = new User(id, "UserServiceImpl", "男" , 12);
		return user;
	}

}
