package com.example.ssm.service.impl;

import com.example.ssm.entity.User;
import com.example.ssm.service.UserService;
import com.example.ssm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 16658
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-09-11 23:32:06
*/

//extends ServiceImpl<UserMapper, User>
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	@Override
	public int save(User user) {
		if (user.getId() != null)
			userMapper.insertAll(user);
		return 0;
	}
}




