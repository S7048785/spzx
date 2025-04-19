package com.atguigu.manager.controller;

import com.atguigu.manager.service.ISysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {

	@Autowired
	private ISysUserService sysUserService ;

	@GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
	public Result<Page<SysRole>> findByPage(SysUserDto sysUserDto ,
												@PathVariable(value = "pageNum") Integer pageNum ,
												@PathVariable(value = "pageSize") Integer pageSize) {
		Page<SysUser> pageInfo = sysUserService.findByPage(sysUserDto , pageNum , pageSize) ;
		return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
	}

	// 用户添加
	@PostMapping(value = "/saveSysUser")
	public Result saveSysUser(@RequestBody SysUser sysUser) {
		sysUserService.save(sysUser) ;
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}

	// 用户修改
	@PutMapping(value = "/updateSysUser")
	public Result updateSysUser(@RequestBody SysUser sysUser) {
		sysUser.setUpdateTime(LocalDateTime.now().toString());
		sysUserService.updateById(sysUser) ;
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}

	// 用户删除
	@DeleteMapping(value = "/deleteById/{userId}")
	public Result deleteById(@PathVariable(value = "userId") Long userId) {
		sysUserService.removeById(userId) ;
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}

	@Operation(summary = "更新角色用户关系")
	@PostMapping("/doAssign")
	public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
		sysUserService.doAssign(assginRoleDto) ;
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}
}