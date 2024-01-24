package com.youzhirong.fbslock.controller;

import com.youzhirong.fbslock.dto.UserDTO;
import com.youzhirong.fbslock.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class TestController {

	@Resource
	private UserService userService;
	
	@ApiOperation(value = "重现并发问题添加用户产生多插入的问题接口")
	@PostMapping("/add/user")
	public void addUser(@RequestBody UserDTO userDTO) {
		userService.saveByDTO(userDTO);
	}
	
	@ApiOperation(value = "使用Redis分布式锁-可重入锁解决并发重复添加用户的接口")
	@PostMapping("/add/user/lock")
	public void addUserLock(@RequestBody UserDTO userDTO) {
		userService.saveByDTOLock(userDTO);
	}
	
	@ApiOperation(value = "使用Redis分布式锁-公平锁解决并发重复添加用户的接口")
	@PostMapping("/add/user/fair/lock")
	public void addUserFairLock(@RequestBody UserDTO userDTO) {
		userService.saveByDTOFairLock(userDTO);
	}
	
	@ApiOperation(value = "查询用户的接口")
	@GetMapping("/select/user/{loginId}")
	public UserDTO selectUser(@PathVariable(name = "loginId") String loginId) {
		AtomicReference<UserDTO> userDTO = new AtomicReference<>(userService.selectByCode(loginId));
		return userDTO.get();
	}
}
