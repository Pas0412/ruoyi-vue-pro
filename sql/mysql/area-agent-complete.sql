-- 地区代理功能完整数据库脚本
-- 包含所有表结构、初始数据和定时任务配置

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

-- ----------------------------
-- 地区代理佣金解冻定时任务配置
-- ----------------------------
INSERT INTO `infra_job` (`id`, `name`, `status`, `handler_name`, `handler_param`, `cron_expression`, `retry_count`, `retry_interval`, `monitor_timeout`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (28, '地区代理佣金解冻 Job', 2, 'tradeAreaAgentRecordUnfreezeJob', '', '0 * * * * ?', 3, 0, 0, '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

-- ----------------------------
-- 地区代理功能菜单权限配置
-- ----------------------------
-- 地区代理管理主菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('地区代理', '', 1, 100, 2072, 'area-agent', 'ep:location', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

-- 获取刚插入的地区代理主菜单ID（假设为2200）
SET @area_agent_menu_id = LAST_INSERT_ID();

-- 地区代理配置菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('地区代理配置', 'trade:area-agent-config:query', 2, 1, @area_agent_menu_id, 'config', '', 'mall/trade/areaAgent/config/index', 'TradeAreaAgentConfig', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

SET @config_menu_id = LAST_INSERT_ID();

-- 地区代理配置权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('地区代理配置查询', 'trade:area-agent-config:query', 3, 1, @config_menu_id, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
('地区代理配置更新', 'trade:area-agent-config:update', 3, 2, @config_menu_id, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

-- 地区代理用户菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('地区代理用户', 'trade:area-agent-user:query', 2, 2, @area_agent_menu_id, 'user', '', 'mall/trade/areaAgent/user/index', 'TradeAreaAgentUser', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

SET @user_menu_id = LAST_INSERT_ID();

-- 地区代理用户权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('地区代理用户查询', 'trade:area-agent-user:query', 3, 1, @user_menu_id, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
('地区代理用户创建', 'trade:area-agent-user:create', 3, 2, @user_menu_id, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
('地区代理用户更新', 'trade:area-agent-user:update', 3, 3, @user_menu_id, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
('地区代理用户删除', 'trade:area-agent-user:delete', 3, 4, @user_menu_id, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
('地区代理用户导出', 'trade:area-agent-user:export', 3, 5, @user_menu_id, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

-- 地区代理佣金记录菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('地区代理佣金记录', 'trade:area-agent-record:query', 2, 3, @area_agent_menu_id, 'record', '', 'mall/trade/areaAgent/record/index', 'TradeAreaAgentRecord', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

SET @record_menu_id = LAST_INSERT_ID();

-- 地区代理佣金记录权限
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('地区代理佣金记录查询', 'trade:area-agent-record:query', 3, 1, @record_menu_id, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
('地区代理佣金记录导出', 'trade:area-agent-record:export', 3, 2, @record_menu_id, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

-- ----------------------------
-- 说明
-- ----------------------------
/*
地区代理功能数据库脚本说明：

1. 表结构：
   - system_area: 地区数据表，存储省市县三级地区信息
   - trade_area_agent_user: 地区代理用户表，存储代理用户信息和佣金统计
   - trade_area_agent_record: 地区代理佣金记录表，存储每笔佣金的详细记录
   - trade_area_agent_config: 地区代理配置表，存储各级代理的佣金比例和冻结天数

2. 初始数据：
   - 地区代理配置：省级1%、市级2%、县区级3%，冻结期30天
   - 定时任务：地区代理佣金解冻Job，每分钟执行一次
   - 菜单权限：完整的管理后台菜单和权限配置

3. 注意事项：
   - 需要先导入地区数据到system_area表
   - 菜单ID可能需要根据实际情况调整
   - 定时任务ID(28)需要确保不与现有任务冲突
*/