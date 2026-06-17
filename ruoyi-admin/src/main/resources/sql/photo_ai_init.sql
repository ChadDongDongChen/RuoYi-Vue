-- AI证件照数据库初始化
-- 适用于 MySQL 5.7+

-- 1. 证件照规格表
CREATE TABLE IF NOT EXISTS `photo_spec` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spec_name` VARCHAR(64) NOT NULL COMMENT '规格名称',
    `width_px` INT NOT NULL DEFAULT 295 COMMENT '宽度像素',
    `height_px` INT NOT NULL DEFAULT 413 COMMENT '高度像素',
    `width_mm` DECIMAL(5,1) DEFAULT 25.0 COMMENT '宽度毫米',
    `height_mm` DECIMAL(5,1) DEFAULT 35.0 COMMENT '高度毫米',
    `price` DECIMAL(10,2) NOT NULL DEFAULT 9.90 COMMENT '价格',
    `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用 0=禁用 1=启用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_enabled_sort` (`enabled`, `sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI证件照规格表';

-- 2. 小程序用户表
CREATE TABLE IF NOT EXISTS `photo_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `openid` VARCHAR(64) NOT NULL COMMENT '微信openid',
    `union_id` VARCHAR(64) DEFAULT NULL COMMENT '微信unionId',
    `nickname` VARCHAR(64) DEFAULT '微信用户' COMMENT '昵称',
    `avatar_url` VARCHAR(512) DEFAULT NULL COMMENT '头像URL',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0=禁用 1=正常',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI证件照小程序用户表';

-- 3. 订单表
CREATE TABLE IF NOT EXISTS `photo_order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` VARCHAR(64) NOT NULL COMMENT '订单号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `original_image_url` VARCHAR(512) DEFAULT NULL COMMENT '原始图片URL',
    `spec_id` BIGINT NOT NULL COMMENT '规格ID',
    `spec_name` VARCHAR(64) DEFAULT NULL COMMENT '规格名称快照',
    `background` VARCHAR(16) NOT NULL DEFAULT '蓝底' COMMENT '背景色',
    `beauty` VARCHAR(16) NOT NULL DEFAULT '自然' COMMENT '美颜级别',
    `suit` VARCHAR(32) NOT NULL DEFAULT '不开启' COMMENT '正装选项',
    `result_image_url` VARCHAR(512) DEFAULT NULL COMMENT '结果图片URL',
    `status` VARCHAR(16) NOT NULL DEFAULT 'pending' COMMENT '状态: pending/paid/completed/cancelled',
    `pay_amount` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '支付金额',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `pay_type` VARCHAR(16) DEFAULT NULL COMMENT '支付方式: wechat',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_order_no` (`order_no`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_status` (`status`),
    INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI证件照订单表';

-- 初始化规格数据
INSERT IGNORE INTO `photo_spec` (`spec_name`, `width_px`, `height_px`, `width_mm`, `height_mm`, `price`, `sort_order`) VALUES
('一寸照', 295, 413, 25.0, 35.0, 9.90, 1),
('二寸照', 413, 579, 35.0, 49.0, 9.90, 2),
('公务员考试', 408, 531, 35.0, 45.0, 9.90, 3),
('教资报名', 240, 320, 20.4, 27.2, 9.90, 4),
('求职简历照', 413, 579, 35.0, 49.0, 9.90, 5),
('护照签证照', 354, 472, 33.0, 48.0, 9.90, 6),
('社保照片', 350, 450, 30.0, 40.0, 9.90, 7),
('小一寸照', 390, 567, 33.0, 48.0, 9.90, 8);
