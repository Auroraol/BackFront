package com.example.experiment01.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.experiment01.entity.TStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 16658
* @description 针对表【t_student】的数据库操作Mapper
* @createDate 2023-09-21 08:26:35
* @Entity com.example.experiment01.entity.TStudent
*/
public interface TStudentMapper extends BaseMapper<TStudent> {
	@Select("select * from t_student")
	List<TStudent> findAllStudent();

}




