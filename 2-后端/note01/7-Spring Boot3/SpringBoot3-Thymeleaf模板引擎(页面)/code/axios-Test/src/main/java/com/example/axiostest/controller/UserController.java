package com.example.axiostest.controller;// UserController.java

import com.example.axiostest.entity.User;
import com.example.axiostest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/add")
	public ResponseEntity<String> addUser(@RequestParam String name,
										  @RequestParam String password,
										  @RequestParam String birthday) {
		// Assume UserService has a method to add a user
		boolean success = userService.addUser(name, password, birthday);

		if (success) {
			return ResponseEntity.ok("User added successfully");
		} else {
			return ResponseEntity.status(500).body("Failed to add user");
		}
	}

	/*
	@PostMapping("/check")
	public ResponseEntity<String> checkUserName(@RequestParam String name) {
		boolean isDuplicate = userService.isUserNameDuplicate(name);

		if (isDuplicate) {
			return ResponseEntity.ok("no");
		} else {
			return ResponseEntity.ok("yes");
		}
	}
	 */
	@PostMapping("/check")
	public ResponseEntity<String> checkUserName(@RequestBody User user) {
		boolean isDuplicate = userService.isUserNameDuplicate(user.getName());

		if (isDuplicate) {
			return ResponseEntity.ok("no");
		} else {
			return ResponseEntity.ok("yes");
		}
	}
}
