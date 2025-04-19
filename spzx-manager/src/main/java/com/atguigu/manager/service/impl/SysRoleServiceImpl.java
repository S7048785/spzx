package com.atguigu.manager.service.impl;

import com.atguigu.manager.mapper.SysRoleMapper;
import com.atguigu.manager.mapper.SysRoleUserMapper;
import com.atguigu.manager.service.ISysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUserRole;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
	private final SysRoleUserMapper sysRoleUserMapper;

	public SysRoleServiceImpl(SysRoleUserMapper sysRoleUserMapper) {
		this.sysRoleUserMapper = sysRoleUserMapper;
	}

	@Override
	public Page<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
		Page<SysRole> page = Page.of(current, limit);
		page(page, Wrappers.lambdaQuery(SysRole.class).like(SysRole::getRoleName, sysRoleDto.getRoleName()));

		return page;
	}

	@Override
	public void updateSysRole(SysRole sysRole) {
		updateById(sysRole);
	}

	@Override
	public Map<String, Object> findAllRoles() {
		List<SysRole> sysRoleList = list();
		Map<String , Object> resultMap = new HashMap<>() ;
		resultMap.put("allRolesList" , sysRoleList) ;
		return resultMap;
	}

	@Override
	public Map<String, Object> findAllRoles(Long userId) {

		// 查询所有的角色数据
		List<SysRole> sysRoleList = list();

		// 查询当前登录用户的角色数据
		List<Long> sysRoles = sysRoleUserMapper.selectObjs(Wrappers.lambdaQuery(SysUserRole.class).select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, userId));

		// 构建响应结果数据
		Map<String , Object> resultMap = new HashMap<>() ;
		resultMap.put("allRolesList" , sysRoleList) ;
		resultMap.put("sysUserRoles", sysRoles);

		return resultMap;
	}
}
