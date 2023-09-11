package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: LFJ
 * @Date: 2023-07-08 22:28
 */
public interface UserService {
	public User show();
	public User get(int id);

}
