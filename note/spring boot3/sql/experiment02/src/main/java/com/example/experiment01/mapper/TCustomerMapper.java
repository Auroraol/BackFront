package com.example.experiment01.mapper;

import com.example.experiment01.entity.TCustomer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 16658
* @description 针对表【t_customer】的数据库操作Mapper
* @createDate 2023-09-21 08:25:45
* @Entity com.example.experiment01.entity.TCustomer
*/
public interface TCustomerMapper extends BaseMapper<TCustomer> {
	List<TCustomer> findCustomerByNameOrJobs(TCustomer c);
	List<TCustomer>	updateCustomerBySet(TCustomer c);
	List<TCustomer> updateCustomerByTrim(TCustomer c);
	List<TCustomer> findByMap(TCustomer c);
	List<TCustomer> findByList(TCustomer c);
	List<TCustomer>	findByArray(TCustomer c);
}




