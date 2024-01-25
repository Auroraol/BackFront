package com.lfj.ssm.experiment01.controller;

import com.lfj.ssm.experiment01.entity.Employee;
import com.lfj.ssm.experiment01.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController   //与前端交互
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/{id}")
    public Integer saveUser(@PathVariable("id") int id){
        log.info("添加id");
        //		EmployeeServiceImpl e = new EmployeeServiceImpl();
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
            employeeService.addEmployeeInfo(employee);
		}
        return 1;
    }
}
