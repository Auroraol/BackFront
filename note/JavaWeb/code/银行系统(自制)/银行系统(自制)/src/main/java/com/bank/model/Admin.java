package com.bank.model;

/**
 * @Author: LFJ
 * @Date: 2023-07-17 9:57
 */
public class Admin {
	private int Id;   //账户id
	private String username;   //账户id
	private String password;    //密码

	public Admin(int id, String username, String password) {
		Id = id;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
