package com.pixelpaw.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 宠物实体 → t_user 表
 */
@Data
@TableName("t_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String phone;       // 手机号，唯一
    private String password;    // BCrypt 哈希
    private String username;    // 昵称
    private String avatar;      // 头像 URL
    private Integer status;     // 1 正常 / 0 封禁


    /** 创建时间（自动填充） */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 更新时间（自动填充） */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /** 逻辑删除 (0=未删, 1=已删) */
    @TableLogic
    private Integer deleted;
}
