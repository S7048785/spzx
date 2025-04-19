package com.atguigu.manager.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.atguigu.manager.mapper.SysRoleMenuMapper;
import com.atguigu.manager.service.ISysMenuService;
import com.atguigu.manager.service.ISysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysRoleMenu;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

	@Autowired
	private ISysMenuService sysMenuService;

	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
		// 查询所有的菜单数据
		List<SysMenu> sysMenuList = sysMenuService.findNodes();

		// 查询当前角色的菜单数据
		List<Long> roleMenuIds = sysRoleMenuMapper.selectObjs(
				Wrappers.lambdaQuery(SysRoleMenu.class).select(SysRoleMenu::getMenuId).eq(SysRoleMenu::getRoleId, roleId).eq(SysRoleMenu::getIsHalf, 0)
		);

		// 将数据存储到Map中进行返回
		Map<String, Object> result = new HashMap<>();
		result.put("sysMenuList", sysMenuList);
		result.put("roleMenuIds", roleMenuIds);

		// 返回
		return result;
	}


	@Transactional
	@Override
	public void doAssign(AssginMenuDto assginMenuDto) {

		// 根据角色的id删除其所对应的菜单数据
		remove(
				Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, assginMenuDto.getRoleId())
		);

		// 获取菜单的id
		List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
		if (menuInfo != null && !menuInfo.isEmpty()) {
			saveBatch(
					CollUtil.map(assginMenuDto.getMenuIdList(), item -> {
						long timestamp = System.currentTimeMillis();
						java.util.Date date = new java.util.Date(timestamp);
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String formattedDate = sdf.format(date);

						return SysRoleMenu.builder()
								.roleId(assginMenuDto.getRoleId())
								.menuId(item.get("id").longValue())
								.createTime(formattedDate)
								.updateTime(formattedDate)
								.isDeleted(0)
								.isHalf(item.get("isHalf").intValue())
								.build();
					}, true));
		}
	}

}

