package com.example.jwttest;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwttest.entity.UserVo;
import com.example.jwttest.utils.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class JwtTestApplicationTests {

	@Autowired
	private JwtUtil jwtUtil;

	@DisplayName("测试1  Token(UserVo userVo)")
	@Test
	void test01() {
		UserVo user = new UserVo();
		user.setName("miaowing");
		user.setId(100);

		String token = jwtUtil.Token(user);
		System.out.println(token);
	}

	@DisplayName("测试2 createToken(Map<String,String> map)")
	@Test
	void test02(){
		HashMap<String, String> map = new HashMap<>();
		map.put("userName", "小明");
		map.put("userId", "11");
		String token = jwtUtil.createToken(map);
		System.out.println(token);
	}


	@DisplayName("verify(String token)")
	@Test
	void  test03(){
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IuWwj-aYjiIsImV4cCI6MTY5NjMyODY0NiwidXNlcklkIjoiMTEifQ.d8Y7G2-mFur7s6lA5lKf-JxwqFENYmgLlij6-g874wc";
		jwtUtil.verifyToken(token, "小明");
	}

	@DisplayName("verify(String token, Class<T> clazz)")
	@Test
	void test04(){
		UserVo user1 = new UserVo();
		user1.setName("神神叨叨");
		user1.setId(100);
		// 设置UserVo的相关属性
		Long expireDate = 1000 * 60 * 60 * 24L; // 设置为1天过期时间
		String token = jwtUtil.Token(user1, expireDate);

		//解析
		UserVo user2 = jwtUtil.verify(token,UserVo.class);
		System.out.println(user2.getName());

	}

}
