package com.atguigu.manager.service;

import com.atguigu.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;


public interface ISysRoleMenuService extends IService<SysRoleMenu> {
	Map<String, Object> findSysRoleMenuByRoleId(Long roleId);
	void doAssign(AssginMenuDto assginMenuDto);
}
