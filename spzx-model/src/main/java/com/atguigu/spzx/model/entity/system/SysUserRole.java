package com.atguigu.spzx.model.entity.system;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SysUserRole extends BaseEntity {

    private Long roleId;       // 角色id
    private Long userId;       // 用户id
    private String createTime;
    private String updateTime;
    private Integer isDeleted;

}
