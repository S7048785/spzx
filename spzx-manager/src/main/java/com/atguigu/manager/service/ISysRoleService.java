package com.atguigu.manager.service;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface ISysRoleService extends IService<SysRole> {
	Page<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

	void updateSysRole(SysRole sysRole);

	Map<String, Object> findAllRoles();

	Map<String, Object> findAllRoles(Long userId);
}
