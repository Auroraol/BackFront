package com.lfj.test3;

import com.lfj.dao.AccountDao;
import com.lfj.entity.Account;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: LFJ
 * @Date: 2023-10-09 14:17
 */
public class FindAccountByIdTest {

	@Test
	public void findAccountByIdTest() {
		// 加载配置文件
		ApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext.xml");
		// 获取AccountDao实例
		AccountDao accountDao =
				(AccountDao) applicationContext.getBean("accountDao");
		// 执行findAccountById()方法
		Account account = accountDao.findAccountById(2);
		System.out.println(account);
	}

}
