-- 地区代理功能相关数据库表
-- 创建时间：2025-01-XX
-- 说明：地区代理功能包括省市县三级代理，支持代理申请、审核、佣金分配等功能

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for trade_regional_agent
-- ----------------------------
DROP TABLE IF EXISTS `trade_regional_agent`;
CREATE TABLE `trade_regional_agent` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `province_id` int NOT NULL COMMENT '省份编号',
  `city_id` int NULL DEFAULT NULL COMMENT '城市编号',
  `district_id` int NULL DEFAULT NULL COMMENT '区县编号',
  `agent_level` tinyint NOT NULL COMMENT '代理级别：1-省级，2-市级，3-县级',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：0-待审核，1-已通过，2-已拒绝，3-已禁用',
  `apply_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申请理由',
  `audit_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核理由',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` bigint NULL DEFAULT NULL COMMENT '审核人编号',
  `agent_price` int NOT NULL DEFAULT 0 COMMENT '可用佣金',
  `frozen_price` int NOT NULL DEFAULT 0 COMMENT '冻结佣金',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_area_level`(`province_id`, `city_id`, `district_id`, `agent_level`, `deleted`, `tenant_id`) USING BTREE COMMENT '地区级别唯一索引',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户编号索引',
  INDEX `idx_status`(`status`) USING BTREE COMMENT '状态索引',
  INDEX `idx_area`(`province_id`, `city_id`, `district_id`) USING BTREE COMMENT '地区索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地区代理表';

-- ----------------------------
-- Table structure for trade_regional_agent_record
-- ----------------------------
DROP TABLE IF EXISTS `trade_regional_agent_record`;
CREATE TABLE `trade_regional_agent_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `agent_id` bigint NOT NULL COMMENT '代理编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '业务编号',
  `biz_type` tinyint NOT NULL DEFAULT 0 COMMENT '业务类型：1-订单，2-提现',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标题',
  `price` int NOT NULL DEFAULT 0 COMMENT '金额',
  `total_price` int NOT NULL DEFAULT 0 COMMENT '当前总佣金',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '说明',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：0-待结算，1-已结算，2-已取消',
  `frozen_days` int NOT NULL DEFAULT 0 COMMENT '冻结时间（天）',
  `unfreeze_time` datetime NULL DEFAULT NULL COMMENT '解冻时间',
  `agent_level` tinyint NOT NULL COMMENT '代理级别：1-省级，2-市级，3-县级',
  `source_user_id` bigint NOT NULL DEFAULT 0 COMMENT '来源用户编号',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单编号',
  `province_id` int NOT NULL COMMENT '省份编号',
  `city_id` int NULL DEFAULT NULL COMMENT '城市编号',
  `district_id` int NULL DEFAULT NULL COMMENT '区县编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_agent_id`(`agent_id`) USING BTREE COMMENT '代理编号索引',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户编号索引',
  INDEX `idx_biz`(`biz_type`, `biz_id`) USING BTREE COMMENT '业务索引',
  INDEX `idx_status`(`status`) USING BTREE COMMENT '状态索引',
  INDEX `idx_area`(`province_id`, `city_id`, `district_id`) USING BTREE COMMENT '地区索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地区代理佣金记录表';

-- ----------------------------
-- Table structure for trade_regional_agent_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `trade_regional_agent_withdraw`;
CREATE TABLE `trade_regional_agent_withdraw` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `agent_id` bigint NOT NULL COMMENT '代理编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `price` int NOT NULL DEFAULT 0 COMMENT '提现金额',
  `fee_price` int NOT NULL DEFAULT 0 COMMENT '提现手续费',
  `total_price` int NOT NULL DEFAULT 0 COMMENT '当前总佣金',
  `type` tinyint NOT NULL DEFAULT 0 COMMENT '提现类型',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '银行名称',
  `bank_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '开户地址',
  `qr_code_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收款码',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：0-审核中，10-审核通过 20-审核不通过；11 - 提现成功；21-提现失败',
  `audit_reason` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核驳回原因',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `pay_transfer_id` bigint NULL DEFAULT NULL COMMENT '转账订单编号',
  `transfer_channel_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '转账渠道',
  `transfer_time` datetime NULL DEFAULT NULL COMMENT '转账支付时间',
  `transfer_error_msg` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '转账错误提示',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_agent_id`(`agent_id`) USING BTREE COMMENT '代理编号索引',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户编号索引',
  INDEX `idx_status`(`status`) USING BTREE COMMENT '状态索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地区代理提现表';

-- ----------------------------
-- 在交易配置表中添加地区代理相关配置字段
-- ----------------------------
ALTER TABLE `trade_config` ADD COLUMN `regional_agent_enabled` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否启用地区代理功能' AFTER `brokerage_withdraw_types`;
ALTER TABLE `trade_config` ADD COLUMN `regional_agent_province_percent` int NOT NULL DEFAULT 0 COMMENT '省级代理佣金比例' AFTER `regional_agent_enabled`;
ALTER TABLE `trade_config` ADD COLUMN `regional_agent_city_percent` int NOT NULL DEFAULT 0 COMMENT '市级代理佣金比例' AFTER `regional_agent_province_percent`;
ALTER TABLE `trade_config` ADD COLUMN `regional_agent_district_percent` int NOT NULL DEFAULT 0 COMMENT '县级代理佣金比例' AFTER `regional_agent_city_percent`;
ALTER TABLE `trade_config` ADD COLUMN `regional_agent_withdraw_min_price` int NOT NULL DEFAULT 0 COMMENT '地区代理提现最低金额' AFTER `regional_agent_district_percent`;
ALTER TABLE `trade_config` ADD COLUMN `regional_agent_withdraw_fee_percent` int NOT NULL DEFAULT 0 COMMENT '地区代理提现手续费百分比' AFTER `regional_agent_withdraw_min_price`;
ALTER TABLE `trade_config` ADD COLUMN `regional_agent_frozen_days` int NOT NULL DEFAULT 0 COMMENT '地区代理佣金冻结时间(天)' AFTER `regional_agent_withdraw_fee_percent`;
ALTER TABLE `trade_config` ADD COLUMN `regional_agent_exclude_spu_ids` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地区代理排除的商品SPU编号列表，逗号分隔' AFTER `regional_agent_frozen_days`;

SET FOREIGN_KEY_CHECKS = 1;