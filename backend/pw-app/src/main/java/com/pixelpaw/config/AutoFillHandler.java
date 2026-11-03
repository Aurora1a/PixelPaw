package com.pixelpaw.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AutoFillHandler implements MetaObjectHandler {

    /** INSERT 时自动填充 */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("Auto fill on insert...");
        // 字段名必须与实体属性名完全一致（实体字段 createdAt ↔ 列 created_at）
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updatedAt", LocalDateTime::now, LocalDateTime.class);
    }

    /** UPDATE 时自动填充 */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("Auto fill on update...");
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime::now, LocalDateTime.class);
    }
}
