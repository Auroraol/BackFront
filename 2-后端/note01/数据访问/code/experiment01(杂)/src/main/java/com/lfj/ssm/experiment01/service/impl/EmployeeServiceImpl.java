package com.lfj.ssm.experiment01.service.impl;

import com.lfj.ssm.experiment01.entity.Employee;
import com.lfj.ssm.experiment01.service.EmployeeService;
import com.lfj.ssm.experiment01.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 16658
* @description 针对表【employee】的数据库操作Service实现
* @createDate 2023-09-14 10:31:39
*/

@Slf4j
@Service
@Component
public class EmployeeServiceImpl implements EmployeeService{
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
				log.info(e.toString());
		}
		else
			log.info("查找员工信息失败");
	}
}




