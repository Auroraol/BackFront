package com.example.experiment01.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.experiment01.entity.TbBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 16658
* @description 针对表【tb_book】的数据库操作Mapper
* @createDate 2023-09-21 08:25:30
* @Entity com.example.experiment01.entity.TbBook
*/
@Mapper
public interface TbBookMapper extends BaseMapper<TbBook> {
	// 根据id查询图书信息
	List<TbBook> findAllById(@Param("id") Integer id);

	// 根据id更新图书信息
	int updateBooknameAndPriceById(@Param("bookname") String bookname, @Param("price") Double price, @Param("id") Integer id);
}




