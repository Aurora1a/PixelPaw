package com.pixelpaw.pet.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 宠物实体 → t_pet 表
 */
@Data
@TableName("t_pet")
public class Pet {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 所属用户 */
    private Long userId;

    /** 宠物名字 */
    private String name;

    /** 物种（SLIME / CAT / DOG / BIRD / FISH） */
    private String species;

    /** 当前形态等级 */
    private Integer formLevel;

    /** 当前等级 */
    private Integer level;

    /** 经验值 */
    private Integer exp;

    /** 饱食度 (0-100) */
    private Integer hunger;

    /** 心情 (0-100) */
    private Integer mood;

    /** 能量值 */
    private Integer energy;

    /** 钻石数 */
    private Integer diamond;

    /** 当前连续签到天数 */
    private Integer currentStreak;

    /** 历史最长连续签到 */
    private Integer maxStreak;

    /** 最后签到时间 */
    private LocalDateTime lastSignAt;

    /** 皮肤 ID */
    private String skin;

    /** JSON 格式的 6x6 像素网格 */
    private String pixelData;

    /** 状态（HEALTHY / HUNGRY / NEAR_STARVATION / DEAD） */
    private String state;

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
