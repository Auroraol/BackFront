package com.lfj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfj.entity.XUser;
import com.lfj.mapper.XUserMapper;
import com.lfj.service.XUserService;
import com.lfj.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 16658
 * @description 针对表【x_user】的数据库操作Service实现
 * @createDate 2023-09-22 21:13:25
 */
@Service
public class XUserServiceImpl extends ServiceImpl<XUserMapper, XUser>
		implements XUserService {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Map<String, Object> login(XUser user) {
		//根据用户名和密码查询
		LambdaQueryWrapper<XUser> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(XUser::getUsername, user.getUsername());
		//wrapper.eq(XUser::getPassword,user.getPassword());
		XUser loginUser = this.baseMapper.selectOne(wrapper);
		//结果不为空,并且密码和传入密码匹配，则生成token,并将用户信息存入redis

		if ((loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword()))) {
			/**  简单方式
			 // 暂时用UUID，终极方案是jwt
			 String key = "user:" + UUID.randomUUID();
			 loginUser.setPassword(null);

			 // 存入redis
			 loginUser.setPassword(null); //密码不存
			 redisTemplate.opsForValue().set(key, loginUser, 30, TimeUnit.MINUTES);// 传入了XUser对象
			 // 返回数据
			 Map<String, Object> data = new HashMap<>();
			 data.put("token", key);
			 return data;
			 **/
			/*
			 * 使用jwt
			 * */
			loginUser.setPassword(null); //密码不存
			Long expireDate = 60L * 60 * 1000 * 1; // 设置为1小时过期时间
			String jwt = jwtUtil.Token(loginUser, expireDate);  // 传入了XUser对象
			//返回数据
			Map<String, Object> data = new HashMap<>();
			data.put("token", jwt);
			return data;

		}
		return null;
	}

	/**
	 * 返回参数:(和前端请求响应结果对应)
	 * {
	 * "code": 20000,
	 * message": "success",
	 * "data": {
	 * "roles": [
	 * "admin"
	 * ],
	 * "name": "admin",
	 * "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
	 * }
	 **/
	@Override
	public Map<String, Object> getUserInfo(String token) {
		/** 简单方式
		 // 从redis查询token
		 Object obj = redisTemplate.opsForValue().get(token);  //  redis
		 // 反序列化 json对象反序列化成user对象
		 XUser user = JSON.parseObject(JSON.toJSONString(obj), XUser.class);
		 if (user != null) {
		 Map<String, Object> data = new HashMap<>();
		 data.put("name", user.getUsername());
		 data.put("avatar", user.getAvatar());
		 // this.getBaseMapper().调用映射文件xml中的方法
		 List<String> roleList = this.getBaseMapper().getRoleNamesByUserId(user.getId());// 查角色
		 data.put("roles", roleList);
		 return data;
		 }
		 return null;
		 **/

		/**
		 *
		 * */
		// 解析jwt
		XUser loginUser = null;
		try {
			loginUser = jwtUtil.verify(token, XUser.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (loginUser != null) {
			Map<String, Object> data = new HashMap<>();
			data.put("name", loginUser.getUsername());
			data.put("avatar", loginUser.getAvatar());
			// this.getBaseMapper().调用映射文件xml中的方法
			List<String> roleList = this.getBaseMapper().getRoleNamesByUserId(loginUser.getId());// 查角色
			data.put("roles", roleList);

			// 权限列表

			return data;
		}
		return null;
	}

	@Override
	public void logout(String token) {
		// 删掉redis中储存的token
		//redisTemplate.delete(token);
	}
}




