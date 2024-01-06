package com.lfj.test3;

import com.lfj.dao.AccountDao;
import com.lfj.entity.Account;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: LFJ
 * @Date: 2023-10-09 13:56
 */
public class TestAddAccount {

	@Test
	public void testAddAccount() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		AccountDao accountDao = context.getBean("accountDao", AccountDao.class);
		//insert into `account`(`id`,`username`,`balance`) values
		// (2,'Joy',3150),(3,Tom',2980),(4,'Jack',6340),(5,'Rose',5489);

		// 给定的账户信息
		int count = 0;
		String[][] accountData = {{"Joy", "3150"}, {"Tom", "2980"}, {"Jack", "6340"}, {"Rose", "5489"}};
		Account account = new Account();

		for (int i = 0; i < accountData.length; i++) {
			String name = accountData[i][0];
			double balance = Double.parseDouble(accountData[i][1]);
			account.setUsername(name);
			account.setBalance(balance);
			// 执行addAccount()方法，并获取返回结果
			int num = accountDao.addAccount(account);
			if (num < 0)
				System.out.println("插入操作执行失败！");
			else
				count++;
		}
		System.out.println("成功插入了" + count + "条数据！");
	}
}
