package com.lfj.test3;

import com.lfj.dao.AccountDao;
import com.lfj.entity.Account;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2023-10-09 14:19
 */
public class FindAllAccountTest {

	@Test
	public void findAllAccountTest() {
		// 加载配置文件
		ApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext.xml");
		// 获取AccountDao实例
		AccountDao accountDao =
				(AccountDao) applicationContext.getBean("accountDao");
		// 执行findAllAccount()方法,获取Account对象的集合
		List<Account> account = accountDao.findAllAccount();
		// 循环输出集合中的对象
		for (Account act : account) {
			System.out.println(act);
		}
	}
}
