package com.lfj.test1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: LFJ
 * @Date: 2023-10-09 11:50
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestJdbcTemplate {

	/**
	 * 调用execute()方法建表
	 */
	@Test
	public void testJdbcTemplate() {

		// 初始化spring容器，加载applicationContext.xml配置
		ApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext.xml");
		// 通过容器，获取JdbcTemplate的实例
		JdbcTemplate jdTemplate =
				(JdbcTemplate) applicationContext.getBean("jdbcTemplate");
		// 使用execute()方法执行SQL语句，创建用户账户管理表account
		jdTemplate.execute("create table account(" +
				"id int primary key auto_increment," +
				"username varchar(50)," +
				"balance double)");
		System.out.println("账户表account创建成功！");

	}
}
