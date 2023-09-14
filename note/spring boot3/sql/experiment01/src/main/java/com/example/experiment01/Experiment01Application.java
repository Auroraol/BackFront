package com.example.experiment01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.experiment01.mapper")
@SpringBootApplication
public class Experiment01Application {

	public static void main(String[] args) {
		SpringApplication.run(Experiment01Application.class, args);
	}

}
