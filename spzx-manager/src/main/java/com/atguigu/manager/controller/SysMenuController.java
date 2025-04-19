package com.atguigu.manager.controller;

import com.atguigu.manager.service.ISysMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spzx.common.exception.GuiguException;

import java.util.List;

@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {

	@Autowired
	private ISysMenuService sysMenuService;

	@Operation(summary = "获取菜单列表")
	@GetMapping("/findNodes")
	public Result<List<SysMenu>> findNodes() {
		List<SysMenu> list = sysMenuService.findNodes();
		return Result.build(list , ResultCodeEnum.SUCCESS) ;
	}

	@Operation(summary = "添加菜单")
	@PostMapping("/save")
	public Result save(@RequestBody SysMenu sysMenu) {
		sysMenuService.saveMenu(sysMenu);
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}

	@Operation(summary = "修改菜单")
	@PutMapping("/updateById")
	public Result updateById(@RequestBody SysMenu sysMenu) {
		sysMenuService.updateById(sysMenu);
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}

	@Operation(summary = "删除菜单")
	@DeleteMapping("/removeById/{id}")
	public Result removeById(@PathVariable Long id) {
		// 先查询有没有子菜单，若有 则不允许删除
		Long count = sysMenuService.lambdaQuery().eq(SysMenu::getParentId, id).count();
		if (count > 0) {
			throw new GuiguException(ResultCodeEnum.NODE_ERROR);
		}
		sysMenuService.removeById(id);
		return Result.build(null , ResultCodeEnum.SUCCESS) ;
	}



}