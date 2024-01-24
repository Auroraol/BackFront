package com.youzhirong.fbslock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.youzhirong.fbslock.mapper.**")
@SpringBootApplication
public class FbslockApplication {

	public static void main(String[] args) {
		SpringApplication.run(FbslockApplication.class, args);
	}

}
