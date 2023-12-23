package com.example.experiment01;

import com.example.experiment01.entity.TbBook;
import com.example.experiment01.entity.TbOrders;
import com.example.experiment01.mapper.TbBookMapper;
import com.example.experiment01.mapper.TbOrdersMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@SpringBootTest
class Experiment01ApplicationTests {

	@Autowired
	private TbBookMapper tbBookMapper;
	@Autowired
	private TbOrdersMapper tbOrdersMapper;

	@Test
	void testTbBookMapper() {

		//1
		List<TbBook> list = tbBookMapper.findAllById(1);
		if (list.size() != 0) {
			for (TbBook e : list)
				log.info("打印id=" + e.getId() + "信息: " + e.toString());
		} else
			log.info("查找图书信息失败");

		//2
		int n = tbBookMapper.updateBooknameAndPriceById("修改", 1000.0, 1);
		if (n == 0) {
			log.info("更新图书信息失败");
		}
	}


	@Test
	void TbOrdersMapper1() {
		List<TbOrders> list = tbOrdersMapper.findOrdersWithP1(1);
		if (list.size() != 0) {
			log.info("查找员工信息成功");
			for (TbOrders e : list)
				log.info("打印id=" + e.getId() + "信息: " + e.toString());
		} else
			log.info("查找员工信息失败");
	}

	@Test
	void TbOrdersMapper2() {
		List<TbOrders> list = tbOrdersMapper.findOrdersWithP2(1);
		if (list.size() != 0) {
			log.info("查找员工信息成功");
			for (TbOrders e : list)
				log.info("打印id=" + e.getId() + "信息: " + e.toString());
		} else
			log.info("查找员工信息失败");
	}
}