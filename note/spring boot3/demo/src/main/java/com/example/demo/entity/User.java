package com.example.demo.entity;

/**
 * @Author: LFJ
 * @Date: 2023-07-08 21:17
 */
public class User {
	int idl;
	String name;
	String sex;
	int age;

	public User(int idl) {
		this.idl = idl;
	}

	public User(int idl, String name, String sex, int age) {
		this.idl = idl;
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"idl=" + idl +
				", name='" + name + '\'' +
				", sex='" + sex + '\'' +
				", age=" + age +
				'}';
	}

	public int getIdl() {
		return idl;
	}

	public void setIdl(int idl) {
		this.idl = idl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
