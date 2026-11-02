-- PixelPaw 数据库初始化脚本
-- 注意：docker-entrypoint-initdb.d 会在 MYSQL_DATABASE=pixelpaw_db 自动创建后执行

USE pixelpaw_db;

-- 用户表
CREATE TABLE t_user (
  id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  phone       VARCHAR(20)  NOT NULL UNIQUE COMMENT '手机号',
  password    VARCHAR(128) NOT NULL COMMENT 'BCrypt哈希',
  username    VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '昵称',
  avatar      VARCHAR(256) NOT NULL DEFAULT '' COMMENT '头像URL',
  status      TINYINT      NOT NULL DEFAULT 1 COMMENT '1正常 0封禁',
  created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted     TINYINT      NOT NULL DEFAULT 0,
  INDEX idx_phone (phone),
  INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 宠物表
CREATE TABLE t_pet (
  id             BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_id        BIGINT UNSIGNED NOT NULL COMMENT '所属用户',
  name           VARCHAR(32)  NOT NULL COMMENT '宠物名字',
  species        VARCHAR(32)  NOT NULL DEFAULT 'SLIME' COMMENT 'SLIME/CAT/DOG/BIRD/FISH',
  form_level     INT          NOT NULL DEFAULT 1 COMMENT '当前形态等级',
  level          INT          NOT NULL DEFAULT 1 COMMENT '当前等级',
  exp            INT          NOT NULL DEFAULT 0 COMMENT '经验值',
  hunger         SMALLINT     NOT NULL DEFAULT 100 COMMENT '饱食度 0-100',
  mood           SMALLINT     NOT NULL DEFAULT 100 COMMENT '心情 0-100',
  energy         INT          NOT NULL DEFAULT 50 COMMENT '能量值',
  diamond        INT          NOT NULL DEFAULT 10 COMMENT '钻石数',
  current_streak INT          NOT NULL DEFAULT 0 COMMENT '当前连续签到天数',
  max_streak     INT          NOT NULL DEFAULT 0 COMMENT '历史最长连续签到',
  last_sign_at   DATETIME              COMMENT '最后签到时间',
  skin           VARCHAR(64)  NOT NULL DEFAULT 'DEFAULT' COMMENT '皮肤ID',
  pixel_data     TEXT                  COMMENT 'JSON格式的6x6像素网格',
  state          VARCHAR(32)  NOT NULL DEFAULT 'HEALTHY' COMMENT 'HEALTHY/HUNGRY/NEAR_STARVATION/DEAD',
  created_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted        TINYINT      NOT NULL DEFAULT 0,
  INDEX idx_user_id (user_id),
  INDEX idx_level (level DESC),
  INDEX idx_streak (current_streak DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 签到日志表
CREATE TABLE t_sign_in_log (
  id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_id       BIGINT UNSIGNED NOT NULL,
  pet_id        BIGINT UNSIGNED NOT NULL,
  date          DATE           NOT NULL COMMENT '签到日期',
  exp_gain      INT            NOT NULL DEFAULT 10,
  energy_gain   INT            NOT NULL DEFAULT 5,
  streak        INT            NOT NULL DEFAULT 1 COMMENT '签到后连续天数',
  created_at    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_user_date (user_id, date),
  INDEX idx_pet_date (pet_id, date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 便签表
CREATE TABLE t_memo (
  id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_id     BIGINT UNSIGNED NOT NULL,
  title       VARCHAR(200)   NOT NULL DEFAULT '' COMMENT '标题',
  content     TEXT           NOT NULL COMMENT '正文内容',
  pin         TINYINT        NOT NULL DEFAULT 0 COMMENT '置顶 1=是',
  sort_order  INT            NOT NULL DEFAULT 0 COMMENT '排序权重',
  created_at  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted     TINYINT        NOT NULL DEFAULT 0,
  INDEX idx_user_created (user_id, created_at DESC),
  INDEX idx_pin_sort (user_id, pin DESC, sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
