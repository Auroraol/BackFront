package com.lfj.ssm.experiment01;

import com.lfj.ssm.experiment01.entity.Employee;
import com.lfj.ssm.experiment01.mapper.EmployeeMapper;
import com.lfj.ssm.experiment01.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@MapperScan("com.lfj.ssm.experiment01.mapper")
@RunWith(SpringRunner.class)
@SpringBootTest
class Experiment01ApplicationTests {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Test
	public void test01(){
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
			int n = employeeMapper.insertSelective(employee);
			if(n > 0)
				log.info("新增员工信息成功");
//			employeeService.addEmployeeInfo(employee);
		}

		//根据id修改员工信息。测试把id=3的员工
		Employee employee2 = new Employee(3, "修改员工", 20, "领导");
		employeeService.alterEmployeeInfoById(employee2, 3);

		//根据id删除员工信息。测试把id=5的员工
		employeeService.deleteEmployeeInfoById(5);

		//根据id查询员工信息。测试把id=3的员工
		employeeService.queryEmployeeInfo(3);
	}
}
