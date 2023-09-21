package com.example.experiment01.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.experiment01.entity.TbPerson;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 16658
* @description 针对表【tb_person】的数据库操作Mapper
* @createDate 2023-09-21 08:26:21
* @Entity com.example.experiment01.entity.TbPerson
*/
public interface TbPersonMapper extends BaseMapper<TbPerson> {
	List<TbPerson> findPersonById();

	List<TbPerson> findPersonById2();
}




