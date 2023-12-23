package cn.lfj.bean;

import org.springframework.context.annotation.Bean;

/**
 * 没有spring注解
 */
public class UserConfiguration {
	@Bean
	public User getUser() {
		User user = new User();
		user.setAge(12);
		user.setUsername("传智播客");
		return user;
	}
}
