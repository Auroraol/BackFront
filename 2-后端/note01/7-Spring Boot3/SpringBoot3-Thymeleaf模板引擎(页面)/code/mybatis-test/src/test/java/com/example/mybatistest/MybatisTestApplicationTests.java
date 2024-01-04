package com.example.mybatistest;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mybatistest.entity.TUser;
import com.example.mybatistest.service.TUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Scanner;

@Slf4j
@SpringBootTest
class MybatisTestApplicationTests {

	@Autowired
	private TUserService tUserService;


	// 增
	public void  addUserInfo(TUser usr) {
		if(tUserService.save(usr))
			log.info("新增学员信息成功");
		else
			log.info("新增学员信息失败");
	}

	//删
	public void  deleteUserInfoById(int id) {
		if(tUserService.removeById(id))
			log.info("删除学员信息成功");
		else
			log.info("删除学员信息失败");
	}

	//改
	public void  alterUserInfoById(TUser usr, String userName) {
		UpdateWrapper updateWrapper = new UpdateWrapper();
		updateWrapper.eq("userName",userName);
		if(tUserService.update(usr, updateWrapper))
			log.info("修改学员信息成功");
		else
			log.info("修改学员信息失败");
	}

	//查
	public void  queryUserInfo(int id) {
		List<TUser> list = (List<TUser>) tUserService.getById(id);
		if(list.size() != 0) {
			log.info("查找学员信息成功");
			for (TUser e : list)
				log.info("打印id=" +  e.getId() + "信息: "+ e.toString());
		}
		else
			log.info("查找学员信息失败");
	}

	@Test
	void contextLoads() {
		Scanner sc = new Scanner(System.in);
		while (true){
			System.out.println("......");
			System.out.println("......");
			System.out.println("......");
			System.out.println("......");
			System.out.println("请输入你的选择");
			int s = sc.nextInt();
			if(s == 100)// 结束程序
				break;
			switch (s){
				case 1: // 新增学员，其他以此类推
					System.out.println("请输入id,姓名,年龄,性别");
					String id = sc.next();
					String name = sc.next();
					int age = sc.nextInt();
					int sex = sc.nextInt();
					////User u = new User(id,name,age,sex);
					//d.addUser(u);
			}
		}

	}
}
