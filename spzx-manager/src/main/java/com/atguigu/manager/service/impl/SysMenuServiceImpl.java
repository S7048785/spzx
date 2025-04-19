package com.atguigu.manager.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.atguigu.manager.mapper.SysMenuMapper;
import com.atguigu.manager.mapper.SysRoleMenuMapper;
import com.atguigu.manager.service.ISysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysRoleMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	public List<SysMenu> findNodes() {
		// 查询所有菜单节点
		List<SysMenu> list = sysMenuMapper.findNodes();

		List<SysMenu> newList = buildTree(list);

		return newList;
	}



	@Override
	public List<SysMenuVo> findUserMenuList() {

		SysUser sysUser = AuthContextUtil.get();
		// 获取当前登录用户的id
		Long userId = sysUser.getId();

		List<SysMenu> sysMenuList = sysMenuMapper.selectListByUserId(userId) ;

		List<SysMenu> list = buildTree(sysMenuList);

		// 封装成VO
		return buildMenus(list);
	}

	@Transactional
	@Override
	public void saveMenu(SysMenu sysMenu) {
		save(sysMenu);
		// 将父菜单设置为半开状态
		Long parentId = sysMenu.getParentId();

		while (parentId != 0) {
			SysMenu sysMenu1 = sysMenuMapper.selectById(parentId);
			sysRoleMenuMapper.update(Wrappers.lambdaUpdate(SysRoleMenu.class).eq(SysRoleMenu::getMenuId, parentId).set(SysRoleMenu::getIsHalf, 1));
			parentId = sysMenu1.getParentId();
		}
	}

	private List<SysMenuVo> buildMenus(List<SysMenu> menus) {
		if (CollUtil.isEmpty(menus)) {
			return null;
		}
		List<SysMenuVo> sysMenuVos = new LinkedList<>();
		for (SysMenu menu : menus) {
			sysMenuVos.add(
					SysMenuVo.builder()
							.title(menu.getTitle())
							.name(menu.getComponent())
							.children(buildMenus(menu.getChildren()))
							.build()
			);
		}
		return sysMenuVos;
	}

	@NotNull
	private static List<SysMenu> buildTree(List<SysMenu> list) {
		// 使用 Map 存储节点，方便快速查找
		Map<Long, SysMenu> map = new HashMap<>();
		list.forEach(item -> {
			map.put(item.getId(), item);
		});

		// 构建树形结构
		List<SysMenu> newList = new ArrayList<>();

		for (SysMenu sysMenu : list) {
			if (sysMenu.getParentId() == 0) {
				newList.add(sysMenu);
			} else {
				// 将当前节点添加到父节点的 children 列表中
				SysMenu parentMenu = map.get(sysMenu.getParentId());
				if (parentMenu.getChildren() == null)
					parentMenu.setChildren(new ArrayList<>());
				parentMenu.getChildren().add(sysMenu);
			}
		}
		return newList;
	}
}
