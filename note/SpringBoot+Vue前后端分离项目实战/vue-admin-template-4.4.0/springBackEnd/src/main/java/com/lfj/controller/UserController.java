package com.lfj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lfj.common.vo.Result;
import com.lfj.entity.XUser;
import com.lfj.service.XUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LFJ
 * @Date: 2023-09-22 21:31
 */

//@Api(tags = "User的相关信息接口")
@Slf4j
@RestController
public class UserController {
	@Autowired
	private XUserService xUserService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 默认
	@GetMapping("/")
	public void hello() {
		log.info("hello");
	}

//
//	@GetMapping("/user")
//	public Result<List<XUser>> getAllUser() {
//		List<XUser> list = xUserService.list();
//		return Result.success(list, "查询成功");
//	}

	/*
	 * 假设前端发送数据, 发过来的数据用RequestBody修饰
	 */
	@PostMapping("/user/login")
	public Result<Map<String, Object>> login(@RequestBody XUser user) {
		// 登录操作
		Map<String, Object> data = xUserService.login(user);
		if (data != null)
			return Result.success(data);
		return Result.fail(20000, "用户密码错误");
	}

	@GetMapping("/user/info")
	public Result<?> getUserInfo(@RequestParam("token") String token) {
		Map<String, Object> data = xUserService.getUserInfo(token);
		if (data != null) {
			return Result.success(data);
		}
		return Result.fail(20003, "用户信息获取失败");
	}

	@PostMapping("/user/logout")
	public Result<?> logout(@RequestHeader("X-Token") String token) {
		xUserService.logout(token);
		return Result.success("注销成功");
	}

	@GetMapping("/user/list")
	public Result<?> getUserListPage(@RequestParam(value = "username", required = false) String username,
									 @RequestParam(value = "phone", required = false) String phone,
									 @RequestParam("pageNo") Long pageNo,
									 @RequestParam("pageSize") Long pageSize) {
		// 直接查询msql
		LambdaQueryWrapper<XUser> wrapper = new LambdaQueryWrapper();
		//等价wrapper.eq(username != null, XUser::getUsername, username);
		wrapper.eq(StringUtils.hasLength(username), XUser::getUsername, username);
		wrapper.eq(phone != null, XUser::getPhone, phone);
		wrapper.orderByDesc(XUser::getId);
		// 分页
		Page<XUser> page = new Page<>(pageNo, pageSize);
		xUserService.page(page, wrapper);

		Map<String, Object> data = new HashMap<>();
		data.put("total", page.getTotal());   //page.getTotal()得添加配置,具体见官网
		data.put("rows", page.getRecords());  //结果集

		return Result.success(data);
		/**data数据:
		 * {
		 *     "code": 20000,
		 *     "message": "success",
		 *     "data": {
		 *         "total": 6,
		 *         "rows": [
		 *             {
		 *                 "id": 1,
		 *                 "username": "admin",
		 *                 "password": "123456",
		 *                 "email": "super@aliyun.com",
		 *                 "phone": "18677778888",
		 *                 "status": 1,
		 *                 "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
		 *                 "deleted": 0
		 *             }
		 *         ]
		 *     }
		 * }
		 * **/
	}

	//RequestBod接受前端数据(如json)
	@PostMapping("/user/add")
	public Result<?> addUser(@RequestBody XUser user) {
//		log.info(user.toString());
		// 用户密码加密
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		xUserService.save(user);
		return Result.success("新增用户成功");
	}

	//更新数据
	@PutMapping("/user/update")
	public Result<?> updateUser(@RequestBody XUser user) {
		user.setPassword(null);
		xUserService.updateById(user);
		return Result.success("修改用户成功");
	}

	// 根据id查找数据
	@GetMapping("/user/{id}")
	public Result<XUser> getUserById(@PathVariable("id") Integer id) {
		XUser user = xUserService.getById(id);
		return Result.success(user);
	}

	// 删除
	@DeleteMapping("/user/{id}")
	public Result<XUser> deleteUserById(@PathVariable("id") Integer id) {
		xUserService.removeById(id);
		return Result.success("删除用户成功");
	}
}
