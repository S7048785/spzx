package com.atguigu.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	List<SysMenu> findNodes();

	List<SysMenu> selectListByUserId(Long userId);
}
