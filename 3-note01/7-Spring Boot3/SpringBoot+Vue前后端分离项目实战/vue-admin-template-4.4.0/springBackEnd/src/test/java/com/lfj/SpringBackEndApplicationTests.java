package com.lfj;

import com.lfj.entity.XUser;
import com.lfj.service.XUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@MapperScan("com.lfj.mapper")   //添加扫描
@SpringBootTest
class SpringBackEndApplicationTests {

	@Autowired
	XUserService userService;

	/**
	 * [插入一条数据]
	 * default boolean save(T entity)
	 * 返回值boolean：true/false
	 * 形参：实体类对象
	 */
	@Test
	void insertOneSave() {
		List<XUser> list = userService.list();
		log.info("查询所有记录{}", list);
	}


}
