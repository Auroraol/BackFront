package com.youzhirong.fbslock;

import com.youzhirong.fbslock.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootTest
class FbsRedissonlockApplicationTests {

	@Resource
	private RestTemplate restTemplate;

	@Test
	void tests() {
		int bingfaSum = 1000;
		for (int i = 0; i < bingfaSum; i++) {
			MyThread thread = new MyThread();
			Thread tt = new Thread(thread);
			tt.start();
		}
	}

	class MyThread implements Runnable {
		@Override
		public void run() {
			String url = "http://localhost:1100/add/user/lock";
			try {
				UserDTO userDTO = new UserDTO();
				userDTO.setLoginId("bingfaLockTest1");
				userDTO.setUserCode("USER300001");
				userDTO.setUserName("并发Lock测试1");
				restTemplate.postForEntity(url, userDTO, String.class);
				UserDTO userDTO1 = new UserDTO();
				userDTO1.setLoginId("bingfaLockTest2");
				userDTO1.setUserCode("USER300002");
				userDTO1.setUserName("并发Lock测试2");
				restTemplate.postForEntity(url, userDTO1, String.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
