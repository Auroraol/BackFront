package com.lfj.test2;

import com.lfj.dao.AccountDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: LFJ
 * @Date: 2023-10-09 13:48
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestDeleteAccount {

	@Test
	public void testDeleteAccount() {
		//1. 获取 Spring 上下文对象，创建的时候需要配置 Spring 的配置文件
		ApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext.xml");
		//2. 获取指定的 Bean 对象
		AccountDao accountDao =
				applicationContext.getBean("accountDao", AccountDao.class);
		// 执行deleteAccount()方法，并获取返回结果
		int num = accountDao.deleteAccount(1);
		if (num > 0) {
			System.out.println("成功删除了" + num + "条数据！");
		} else {
			System.out.println("删除操作执行失败！");
		}
	}
}
