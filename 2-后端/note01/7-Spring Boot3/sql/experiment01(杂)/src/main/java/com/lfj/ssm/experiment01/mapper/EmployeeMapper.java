package com.lfj.ssm.experiment01.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lfj.ssm.experiment01.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

/**
* @author 16658
* @description 针对表【employee】的数据库操作Mapper
* @createDate 2023-09-14 10:31:39
* @Entity com.lfj.ssm.experiment01.entity.Employee
*/

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
	//（1）根据id查询员工信息。
	List<Employee> selectById(@Param("id") Integer id);
	//（2）新增员工信息.
	int insertSelective(Employee employee);
	//（3）根据id修改员工信息。
	int updateIdAndNameAndAgeAndPositionById(@Param("id") Integer id,
											 @Param("name") String name, @Param("age") Integer age, @Param("position") String position, @Param("oldId") Integer oldId);
	//（4）根据id删除员工信息。
	int deleteById(@Param("id") Integer id);
}




