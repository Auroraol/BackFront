package com.youzhirong.fbslock;

import com.youzhirong.fbslock.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootTest
class FbslockApplicationTests {

	@Resource
	private RestTemplate restTemplate;

	@Test
	void tests() {
		int bingfaSum = 30;
		for (int i = 0; i < bingfaSum; i++) {
			MyThread thread = new MyThread();
			Thread tt = new Thread(thread);
			tt.start();
		}
	}

	class MyThread implements Runnable {
		@Override
		public void run() {
			String url = "http://localhost:1100/add/user";
			try {
				UserDTO userDTO = new UserDTO();
				userDTO.setLoginId("bingfaTest1");
				userDTO.setUserCode("USER200001");
				userDTO.setUserName("并发测试1");
				restTemplate.postForEntity(url, userDTO, String.class);
				UserDTO userDTO1 = new UserDTO();
				userDTO1.setLoginId("bingfaTest2");
				userDTO1.setUserCode("USER200002");
				userDTO1.setUserName("并发测试2");
				restTemplate.postForEntity(url, userDTO1, String.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
