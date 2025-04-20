package com.atguigu.manager.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 设置创建时间和创建人
        this.strictInsertFill(metaObject, "createTime", String.class, DateUtil.now());
        this.strictInsertFill(metaObject, "isDeleted", Integer.class, 0);

        // 如果有其他需要在插入时填充的字段，可以继续添加
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 设置更新时间和更新人
        this.strictUpdateFill(metaObject, "updateTime", String.class, DateUtil.now());

        // 如果有其他需要在更新时填充的字段，可以继续添加
    }
}