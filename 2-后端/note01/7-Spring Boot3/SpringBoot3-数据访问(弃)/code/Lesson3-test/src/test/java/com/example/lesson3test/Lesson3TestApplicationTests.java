package com.example.lesson3test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.lesson3test.entity.TUser;
import com.example.lesson3test.service.TUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Scanner;

@SpringBootTest
class Lesson3TestApplicationTests {
	@Autowired
	private TUserService tUserService;


	// 增
	public void  addUserInfo(TUser usr) {
		if(!tUserService.save(usr))
			System.out.println("新增学员信息失败");
	}

	// 删
	public void  deleteUserInfoById(int id) {
		if(!tUserService.removeById(id))
			System.out.println("删除学员信息失败");
	}

	// 改
	public void  alterUserInfoById(TUser usr, String userName) {
		UpdateWrapper updateWrapper = new UpdateWrapper();
		updateWrapper.eq("name",userName);
		updateWrapper.set("name", usr.getName());
		updateWrapper.set("age", usr.getAge());
		updateWrapper.set("sex", usr.getSex());

		if(!tUserService.update(updateWrapper))
			System.out.println("修改学员信息失败");
	}

	// 查
	public void queryUserInfo(int id) {
		TUser user = tUserService.getById(id);
		if (user != null) {
			System.out.println("打印id=" + user.getId() + "  信息: " + user.toString());
		} else {
			System.out.println("查找学员信息失败");
		}
	}

	public void  queryUsersInfo() {
		List<TUser> list = tUserService.list();
		if(list.size() != 0) {
			for (TUser e : list)
				System.out.println("打印id=" +  e.getId() + "  信息: "+ e.toString());
		}
		else {
			System.out.println("查找学员信息失败");
		}
	}

	// 排序
	public void sortUsersInfo(String column, int ascending) {
		QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
		if (ascending == 1) {
			queryWrapper.orderByAsc(column);
		} else {
			queryWrapper.orderByDesc(column);
		}

		List<TUser> list = tUserService.list(queryWrapper);
		if (list.size() != 0) {
			for (TUser e : list)
				System.out.println("打印id=" +  e.getId() + "  信息: "+ e.toString());
		}else{
			System.out.println("查找学员信息失败");
		}
	}

	// main
	@Test
	void contextLoads() {
		Scanner sc = new Scanner(System.in);
		while (true){
			System.out.println("..............");
			System.out.println("1:  新增学员");
			System.out.println("2:  删除学员");
			System.out.println("3:  修改学员");
			System.out.println("4:  id查询学员");
			System.out.println("5:  查询所有学员");
			System.out.println("6:  排序学员");
			System.out.println("7:  退出");
			System.out.println("..............");
			int s = sc.nextInt();
			if(s == 7)// 结束程序
				break;
			switch (s){
				case 1: // 新增学员，其他以此类推
					System.out.println("请输入id,姓名,年龄,性别");
					int newId = sc.nextInt();
					String newName = sc.next();
					int newAge = sc.nextInt();
					String newSex = sc.next();
					TUser newUser = new TUser(newId, newName, newAge, newSex);
					addUserInfo(newUser);
					break;
				case 2: // 删除学员
					System.out.println("请输入要删除的学员id");
					int deleteId = sc.nextInt();
					deleteUserInfoById(deleteId);
					break;
				case 3: // 修改学员
					System.out.println("请输入要修改的学员姓名");
					String modifyName = sc.next();
					System.out.println("请输入新的姓名,年龄,性别");
					String modifyNewName = sc.next();
					int modifyNewAge = sc.nextInt();
					String modifyNewSex = sc.next();
					TUser modifiedUser = new TUser();
					modifiedUser.setName(modifyNewName);
					modifiedUser.setAge(modifyNewAge);
					modifiedUser.setSex(modifyNewSex);
					alterUserInfoById(modifiedUser, modifyName);
					break;
				case 4: // 查询学员
					System.out.println("请输入要查询的学员id");
					int queryId = sc.nextInt();
					queryUserInfo(queryId);
					break;
				case 5: // 查询学员
					queryUsersInfo();
					break;
				case 6: // 排序学员
					System.out.println("请输入排序规则id/name/age/sex");
					String column = sc.next();
					System.out.println("请输入排序规则升序1, 降序0");
					int ascending = sc.nextInt();
					sortUsersInfo(column, ascending);
					break;
				default:
					System.out.println("无效的选择");
					break;
			}
		}
	}
}
