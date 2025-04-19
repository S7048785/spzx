package com.atguigu.spzx.model.entity.system;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "系统菜单实体类")
@Data
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

	@Schema(description = "父节点id")
	@TableField("parent_id")
	private Long parentId;

	@Schema(description = "节点标题")
	@TableField("title")
	private String title;

	@Schema(description = "组件名称")
	@TableField("component")
	private String component;

	@Schema(description = "排序值")
	@TableField("sort_value")
	private Integer sortValue;

	@Schema(description = "状态(0:禁止,1:正常)")
	@TableField("status")
	private Integer status;

	// 下级列表
	@Schema(description = "子节点")
	@TableField(exist = false)
	private List<SysMenu> children;

}