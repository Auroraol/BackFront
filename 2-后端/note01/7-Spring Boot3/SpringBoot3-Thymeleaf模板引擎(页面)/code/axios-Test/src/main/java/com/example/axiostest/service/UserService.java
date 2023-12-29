package com.example.axiostest.service;// UserService.java

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

	private Set<String> userNames = new HashSet<>();

	public boolean addUser(String name, String password, String birthday) {
		// Check if the username is already taken
		if (isUserNameDuplicate(name)) {
			return false; // Username is already taken
		}

		// Add the user (you can perform additional logic here)
		userNames.add(name);
		return true;
	}

	public boolean isUserNameDuplicate(String name) {
		// Check if the username is already in use
		return userNames.contains(name);
	}
}
