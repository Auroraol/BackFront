package com.lfj.test2;

import com.lfj.dao.AccountDao;
import com.lfj.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: LFJ
 * @Date: 2023-10-09 12:03
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestAddAccount {

	@Test
	public void testAddAccount() {
		//1. 获取 Spring 上下文对象，创建的时候需要配置 Spring 的配置文件
		ApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext.xml");
		//2. 获取指定的 Bean 对象
		AccountDao accountDao =
				applicationContext.getBean("accountDao", AccountDao.class);
		// 创建Account对象，并向Account对象中添加数据
		Account account = new Account();
		account.setUsername("tom");
		account.setBalance(1000.00);
		// 执行addAccount()方法，并获取返回结果
		int num = accountDao.addAccount(account);
		if (num > 0) {
			System.out.println("成功插入了" + num + "条数据！");
		} else {
			System.out.println("插入操作执行失败！");
		}
	}
}

