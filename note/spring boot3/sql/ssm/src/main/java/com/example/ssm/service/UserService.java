package com.example.ssm.service;

import com.example.ssm.entity.User;
import com.example.ssm.mapper.UserMapper;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author 16658
* @description 针对表【user】的数据库操作Service
* @createDate 2023-09-11 23:32:06
*/

// extends IService<User>
public interface UserService{
	public int save(User user);
}
