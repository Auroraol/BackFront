package com.lfj.ssm.experiment01.service;

import com.lfj.ssm.experiment01.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 16658
* @description 针对表【employee】的数据库操作Service
* @createDate 2023-09-14 10:31:39
*/
public interface EmployeeService{
	void  addEmployeeInfo(Employee employee);
	void  deleteEmployeeInfoById(int id);
	void  alterEmployeeInfoById(Employee employee, int id);
	void  queryEmployeeInfo(int id);
}
