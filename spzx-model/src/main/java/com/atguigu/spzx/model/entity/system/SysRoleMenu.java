package com.atguigu.spzx.model.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class SysRoleMenu {

	private Long id;
	private Long roleId;
	private Long menuId;
	private String createTime;
	private String updateTime;
	private Integer isDeleted;
	private Integer isHalf;
}
