package com.atguigu.manager.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ISysUserService extends IService<SysUser>  {
	LoginVo login(LoginDto loginDto);

	SysUser getUserInfo(String token);

	void logout(String token);

	Page<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

	void doAssign(AssginRoleDto assginRoleDto);
}
