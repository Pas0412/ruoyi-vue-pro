-- 地区代理功能相关表结构

-- ----------------------------
-- Table structure for system_area
-- ----------------------------
DROP TABLE IF EXISTS `system_area`;
CREATE TABLE `system_area` (
  `id` int NOT NULL COMMENT '地区编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '地区名称',
  `type` tinyint NOT NULL COMMENT '地区类型：1-国家，2-省，3-市，4-县区',
  `parent_id` int NOT NULL DEFAULT 0 COMMENT '父级编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id` (`parent_id`) USING BTREE,
  INDEX `idx_type` (`type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地区数据表';

-- ----------------------------
-- Table structure for trade_area_agent_user
-- ----------------------------
DROP TABLE IF EXISTS `trade_area_agent_user`;
CREATE TABLE `trade_area_agent_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `area_id` int NOT NULL COMMENT '代理地区编号（对应system_area中的id）',
  `area_level` tinyint NOT NULL COMMENT '代理等级：2-省级，3-市级，4-县区级',
  `area_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '地区名称',
  `parent_area_id` int NULL DEFAULT NULL COMMENT '上级地区编号',
  `agent_enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用代理资格',
  `agent_time` datetime NULL DEFAULT NULL COMMENT '成为代理时间',
  `commission_price` int NOT NULL DEFAULT 0 COMMENT '可用佣金（分）',
  `frozen_price` int NOT NULL DEFAULT 0 COMMENT '冻结佣金（分）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE COMMENT '用户唯一约束',
  UNIQUE KEY `uk_area_id` (`area_id`) USING BTREE COMMENT '地区唯一约束',
  INDEX `idx_area_level` (`area_level`) USING BTREE,
  INDEX `idx_parent_area_id` (`parent_area_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地区代理用户表';

-- ----------------------------
-- Table structure for trade_area_agent_record
-- ----------------------------
DROP TABLE IF EXISTS `trade_area_agent_record`;
CREATE TABLE `trade_area_agent_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '代理用户编号',
  `area_id` int NOT NULL COMMENT '代理地区编号',
  `area_level` tinyint NOT NULL COMMENT '代理等级：2-省级，3-市级，4-县区级',
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '业务编号（订单号等）',
  `biz_type` tinyint NOT NULL DEFAULT 1 COMMENT '业务类型：1-订单，2-提现',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标题',
  `price` int NOT NULL DEFAULT 0 COMMENT '佣金金额（分）',
  `total_price` int NOT NULL DEFAULT 0 COMMENT '当前总佣金（分）',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '说明',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：0-待结算，1-已结算，2-已取消',
  `frozen_days` int NOT NULL DEFAULT 0 COMMENT '冻结时间（天）',
  `unfreeze_time` datetime NULL DEFAULT NULL COMMENT '解冻时间',
  `source_user_id` bigint NOT NULL DEFAULT 0 COMMENT '来源用户编号（下单用户）',
  `order_area_id` int NULL DEFAULT NULL COMMENT '订单收货地区编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id` (`user_id`) USING BTREE,
  INDEX `idx_biz` (`biz_type`, `biz_id`) USING BTREE,
  INDEX `idx_status` (`status`) USING BTREE,
  INDEX `idx_area_level` (`area_level`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地区代理佣金记录表';

-- ----------------------------
-- Table structure for trade_area_agent_config
-- ----------------------------
DROP TABLE IF EXISTS `trade_area_agent_config`;
CREATE TABLE `trade_area_agent_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `area_level` tinyint NOT NULL COMMENT '代理等级：2-省级，3-市级，4-县区级',
  `commission_percent` decimal(5,2) NOT NULL DEFAULT 0.00 COMMENT '佣金比例（%）',
  `frozen_days` int NOT NULL DEFAULT 0 COMMENT '佣金冻结天数',
  `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_area_level` (`area_level`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地区代理配置表';

-- ----------------------------
-- 初始化地区代理配置数据
-- ----------------------------
INSERT INTO `trade_area_agent_config` (`area_level`, `commission_percent`, `frozen_days`, `enabled`) VALUES
(2, 1.00, 30, b'1'),  -- 省级代理：1%佣金，冻结30天
(3, 2.00, 30, b'1'),  -- 市级代理：2%佣金，冻结30天
(4, 3.00, 30, b'1');  -- 县区级代理：3%佣金，冻结30天