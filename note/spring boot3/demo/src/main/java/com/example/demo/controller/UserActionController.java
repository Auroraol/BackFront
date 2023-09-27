//package com.example.demo.controller;
//import com.example.demo.entity.User;
//import com.example.demo.service.UserService;
//import com.example.demo.service.impl.UserServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
///**
// */
//
//@Slf4j
//@RestController   // 返回对象
//@RequestMapping("/action")
//public class UserActionController {
//
//	/***** 常规用法:******/
//	private UserService userService;
//
//	public User show(){
//		userService = new UserServiceImpl();
//		return userService.get();
//	}
//
//	@GetMapping("/{id}")
//	public User get(@PathVariable("id") int id){
//		userService = new UserServiceImpl();
//		return userService.get(id);
//	}
//
//	/*
//	 * 新增数据
//	 * 假设前端发送数据  前端发过来的数据用RequestBody修饰
//	 */
//	@PostMapping
//	public User getById(@RequestBody User user){
//		log.info("user:  " + user);
//		return user;
//	}
//}

package com.example.demo.controller;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 */

@Slf4j
@RestController   // 返回对象
@RequestMapping("/action")
public class UserActionController {


	/*
	* @Autowired   //自动加载类
	* private UserService userService;  //报错UserService接口里有多个实现类
	*/
	@Autowired
	@Qualifier("adminServiceImpl")    //首字母小写
	private UserService userService;

	/* @Resource   和 Autowired 没有啥区别,  @Resource可以指定UserServiceImpl类
	* @Resource(name = "AdminServiceImpl")
	* private UserService userService;  // ok
	*/
	@Resource(name = "UserServiceImpl")
	private UserService userService1;  // ok


	@GetMapping("/show")
	public User show(){
		return userService.show();
	}

	@GetMapping("/{id}")
	public User get(@PathVariable("id") int id){
		return userService.get(id);
	}


	/*
	 * 新增数据
	 * 假设前端发送数据  前端发过来的数据用RequestBody修饰
	 */
	@PostMapping
	public User addUser(@RequestBody User user){
		return user;
	}


	@Value("${robot.name}")
	private String naem;    // name ={robot.name}

}
