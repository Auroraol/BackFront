package com.lfj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lfj.common.vo.Result;
import com.lfj.entity.XRole;
import com.lfj.service.XRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LFJ
 * @Date: 2023-09-28 11:50
 */
@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private XRoleService roleService;

	@GetMapping("/list")
	public Result<Map<String, Object>> getUserList(@RequestParam(value = "roleName", required = false) String roleName,
												   @RequestParam(value = "pageNo") Long pageNo,
												   @RequestParam(value = "pageSize") Long pageSize) {
		// 直接查询msql
		LambdaQueryWrapper<XRole> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(StringUtils.hasLength(roleName), XRole::getRoleName, roleName);
		wrapper.orderByDesc(XRole::getRoleId);

		Page<XRole> page = new Page<>(pageNo, pageSize);
		roleService.page(page, wrapper);

		Map<String, Object> data = new HashMap<>();
		data.put("total", page.getTotal());
		data.put("rows", page.getRecords());

		return Result.success(data);

	}

	@PostMapping
	public Result<?> addRole(@RequestBody XRole role) {
		roleService.save(role);
		return Result.success("新增角色成功");
	}

	@PutMapping
	public Result<?> updateRole(@RequestBody XRole role) {
		roleService.updateById(role);
		return Result.success("修改角色成功");
	}

	@GetMapping("/{id}")
	public Result<XRole> getRoleById(@PathVariable("id") Integer id) {
		XRole role = roleService.getById(id);
		return Result.success(role);
	}

	@DeleteMapping("/{id}")
	public Result<XRole> deleteRoleById(@PathVariable("id") Integer id) {
		roleService.removeById(id);
		return Result.success("删除角色成功");
	}

}