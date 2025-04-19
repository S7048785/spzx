package com.atguigu.manager.service;

import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {
	List<SysMenu> findNodes();

	List<SysMenuVo> findUserMenuList();

	void saveMenu(SysMenu sysMenu);

}
