package com.atguigu.boot3.ssm.service;

import com.atguigu.boot3.ssm.enutity.TUser;
import com.atguigu.boot3.ssm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: LFJ
 * @Date: 2023-07-09 10:46
 */

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public int save(TUser user){
		if (user.getId() == null){// user没有id，则表示是新增
			userMapper.insert(user);
		}else {//否则为更新
			userMapper.update(user);
		}
		return 0;
	}

}
