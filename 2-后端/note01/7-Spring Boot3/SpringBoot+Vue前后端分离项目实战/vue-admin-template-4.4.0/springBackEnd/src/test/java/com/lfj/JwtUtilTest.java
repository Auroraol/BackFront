package com.lfj;

import com.lfj.entity.XUser;
import com.lfj.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class JwtUtilTest {

	@Autowired
	private JwtUtil jwtUtil;

	@DisplayName("测试1  Token(UserVo userVo)")
	@Test
	void test01() {
		XUser user = new XUser();
		user.setUsername("miaowing");
		user.setId(100);

		String token = jwtUtil.Token(user);
		System.out.println(token);
	}

	@DisplayName("测试2 createToken(Map<String,String> map)")
	@Test
	void test02() {
		HashMap<String, String> map = new HashMap<>();
		map.put("userName", "小明");
		map.put("userId", "11");
		String token = jwtUtil.createToken(map);
		System.out.println(token);
	}


	@DisplayName("verify(String token, Class<T> clazz)")
	@Test
	void test04() {
		XUser user1 = new XUser();
		user1.setUsername("神神叨叨");
		user1.setId(100);
		// 设置UserVo的相关属性
		Long expireDate = 1000 * 60 * 60 * 24L; // 设置为1天过期时间
		String token = jwtUtil.Token(user1, expireDate);

		//解析
		XUser user2 = jwtUtil.verify(token, XUser.class);
		System.out.println(user2.getUsername());

	}

}
