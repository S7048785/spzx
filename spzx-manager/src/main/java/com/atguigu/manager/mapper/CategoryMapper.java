package com.atguigu.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
	List<Category> selectByParentId(Long parentId);

	void insertCategoryBatch(List<Category> map);
}
