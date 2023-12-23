package com.example.experiment01;

import com.example.experiment01.entity.Employee;
import com.example.experiment01.mapper.EmployeeMapper;
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
	private EmployeeMapper employeeMapper;

	// 增
	public void  addEmployeeInfo(Employee employee) {
		int n = employeeMapper.insertSelective(employee);
		if(n > 0)
			log.info("新增员工信息成功");
		else
			log.info("新增员工信息失败");
	}

	//删
	public void  deleteEmployeeInfoById(int id) {
		int n = employeeMapper.deleteById(id);
		if(n > 0)
			log.info("删除员工信息成功");
		else
			log.info("删除员工信息失败");
	}

	//改
	public void  alterEmployeeInfoById(Employee employee, int id) {
		int n = employeeMapper.updateIdAndNameAndAgeAndPositionById(employee.getId(),
				employee.getName(),employee.getAge(),employee.getPosition(),id);
		if(n > 0)
			log.info("修改员工信息成功");
		else
			log.info("修改员工信息失败");
	}

	//查
	public void  queryEmployeeInfo(int id) {
		List<Employee> list = employeeMapper.selectById(id);
		if(list.size() != 0) {
			log.info("查找员工信息成功");
			for (Employee e : list)
				log.info("打印id=" +  e.getId() + "信息: "+ e.toString());
		}
		else
			log.info("查找员工信息失败");
	}

	@Test
	void contextLoads() {
		List<String> position = new ArrayList<String>();
		position.add("员工");
		position.add("经理");
		position.add("领导");
		Employee employee = new Employee();
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			employee.setId(i);
			employee.setAge(i + 10);
			employee.setName("小" + i);
			employee.setPosition(position.get(rand.nextInt(3)));
			//新增员工信息
			addEmployeeInfo(employee);
		}

		//根据id修改员工信息。测试把id=3的员工
		Employee employee2 = new Employee(3, "修改员工", 20, "领导");
		alterEmployeeInfoById(employee2, 3);

		//根据id删除员工信息。测试把id=5的员工
		deleteEmployeeInfoById(5);

		//根据id查询员工信息。测试把id=3的员工
		queryEmployeeInfo(3);
	}
}
