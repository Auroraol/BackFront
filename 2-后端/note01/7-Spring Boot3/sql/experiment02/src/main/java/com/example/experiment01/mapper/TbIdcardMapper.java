package com.example.experiment01.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.experiment01.entity.TbIdcard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 16658
* @description 针对表【tb_idcard】的数据库操作Mapper
* @createDate 2023-09-21 08:25:55
* @Entity com.example.experiment01.entity.TbIdcard
*/
public interface TbIdcardMapper extends BaseMapper<TbIdcard> {
	// 根据id查询证件信息
	List<TbIdcard> findAllById(@Param("id") Integer id);

	//
	List<TbIdcard> findCodeById(@Param("id") Integer id);
}




