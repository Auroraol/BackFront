package com.example.mybatistest.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatistest.entity.TUser;
import com.example.mybatistest.service.TUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LFJ
 * @Date: 2024-01-03 13:26
 */

@Api(tags = "排序")
@RestController
@RequestMapping("sort")
public class SortController {

	@Autowired
	private com.example.mybatistest.service.TUserService TUserService;

	@ApiOperation("默认顺序")
	@GetMapping("defaultOrder")
	public IPage<TUser> defaultOrder(Page<TUser> page) {
		return TUserService.page(page);
	}

	@ApiOperation("通过orderItems")
	@GetMapping("orderItems")
	public IPage<TUser> orderItems(Page<TUser> page) {
		page.addOrder(OrderItem.desc("create_time"));
		// 可以指定多列。比如下边这个：按create_time排序，若create_time相同，则按id排序
		// page.addOrder(OrderItem.desc("create_time"), OrderItem.asc("id"));
		return TUserService.page(page);
	}

	@ApiOperation("通过wrapper")
	@GetMapping("wrapper")
	public IPage<TUser> wrapper(Page<TUser> page) {
		LambdaQueryWrapper<TUser> queryWrapper = Wrappers.<TUser>lambdaQuery();
		// 按create_time排序，若create_time相同，则按id排序
		queryWrapper.orderByDesc(TUser::getCreateTime);
		queryWrapper.orderByAsc(TUser::getId);
		return TUserService.page(page, queryWrapper);
	}

	@ApiOperation("前端指定顺序")
	@GetMapping("byFrontEnd")
	public IPage<TUser> byFrontEnd(Page<TUser> page) {
		return TUserService.page(page);
	}
}
