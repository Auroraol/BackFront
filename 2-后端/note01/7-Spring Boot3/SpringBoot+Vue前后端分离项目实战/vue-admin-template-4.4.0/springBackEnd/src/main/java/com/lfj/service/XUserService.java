package com.lfj.service;

import com.lfj.entity.XUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author 16658
* @description 针对表【x_user】的数据库操作Service
* @createDate 2023-09-22 21:13:25
*/
public interface XUserService extends IService<XUser> {

	public Map<String, Object> login(XUser user);

	Map<String, Object> getUserInfo(String token);

	void logout(String token);
}
