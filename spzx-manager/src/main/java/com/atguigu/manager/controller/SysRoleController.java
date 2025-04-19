package com.atguigu.manager.controller;

import com.atguigu.manager.service.ISysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "角色接口")
@RequestMapping("/admin/system/sysRole")
@RestController
public class SysRoleController {

	@Autowired
	private ISysRoleService sysRoleService;

	@Operation(summary = "获取角色列表")
	@PostMapping("findByPage/{current}/{limit}")
	public Result findByPage(
			@PathVariable("current") Integer current,
			@PathVariable("limit") Integer limit,
			@RequestBody SysRoleDto sysRoleDto) {

		Page<SysRole> page = sysRoleService.findByPage(sysRoleDto, current, limit);
		return Result.build(page, ResultCodeEnum.SUCCESS);
	}

	@Operation(summary = "添加角色")
	@PostMapping(value = "/saveSysRole")
	public Result saveSysRole(@RequestBody SysRole SysRole) {
		sysRoleService.save(SysRole) ;
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}

	@Operation(summary = "修改角色")
	@PutMapping(value = "/updateSysRole")
	public Result updateSysRole(@RequestBody SysRole sysRole) {
		sysRoleService.updateSysRole(sysRole) ;
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}

	@Operation(summary = "删除角色")
	@DeleteMapping(value = "/deleteById/{roleId}")
	public Result deleteById(@PathVariable(value = "roleId") Long roleId) {
		sysRoleService.removeById(roleId) ;
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}

	@GetMapping(value = "/findAllRoles")
	public Result<Map<String , Object>> findAllRoles() {
		Map<String, Object> resultMap = sysRoleService.findAllRoles();
		return Result.build(resultMap , ResultCodeEnum.SUCCESS)  ;
	}

	@GetMapping(value = "/findAllRoles/{userId}")
	public Result<Map<String , Object>> findAllRoles(@PathVariable(value = "userId") Long userId) {
		Map<String, Object> resultMap = sysRoleService.findAllRoles(userId);
		return Result.build(resultMap , ResultCodeEnum.SUCCESS)  ;
	}
}
