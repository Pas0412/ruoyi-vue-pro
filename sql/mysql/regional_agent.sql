-- 地区代理相关表结构

-- 地区代理表
CREATE TABLE `product_regional_agent` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `area_id` int NOT NULL COMMENT '地区编号',
  `area_type` tinyint NOT NULL COMMENT '地区类型',
  `area_name` varchar(255) NOT NULL COMMENT '地区名称',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '代理状态',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` bigint DEFAULT NULL COMMENT '审核人',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `brokerage_price` int NOT NULL DEFAULT '0' COMMENT '可用佣金，单位：分',
  `frozen_brokerage_price` int NOT NULL DEFAULT '0' COMMENT '冻结佣金，单位：分',
  `agent_time` datetime DEFAULT NULL COMMENT '成为代理时间',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_area` (`user_id`, `area_id`, `deleted`),
  KEY `idx_area_id` (`area_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地区代理表';

-- 地区代理记录表
CREATE TABLE `product_regional_agent_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `biz_id` varchar(64) NOT NULL COMMENT '业务编号',
  `biz_type` tinyint NOT NULL COMMENT '业务类型',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `description` varchar(1000) DEFAULT NULL COMMENT '说明',
  `price` int NOT NULL COMMENT '金额，单位：分',
  `total_price` int NOT NULL COMMENT '当前总佣金，单位：分',
  `status` tinyint NOT NULL COMMENT '状态',
  `frozen_time` datetime DEFAULT NULL COMMENT '冻结时间',
  `unfreeze_time` datetime DEFAULT NULL COMMENT '解冻时间',
  `source_user_level` tinyint DEFAULT NULL COMMENT '来源用户等级',
  `source_user_id` bigint DEFAULT NULL COMMENT '来源用户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_biz_id` (`biz_id`),
  KEY `idx_biz_type` (`biz_type`),
  KEY `idx_status` (`status`),
  KEY `idx_unfreeze_time` (`unfreeze_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地区代理记录表';

-- 地区代理提现表
CREATE TABLE `product_regional_agent_withdraw` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `tenant_id` bigint NOT NULL DEFAULT 1 COMMENT '租户编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `price` int NOT NULL COMMENT '提现金额，单位：分',
  `fee_price` int NOT NULL DEFAULT '0' COMMENT '手续费，单位：分',
  `total_price` int NOT NULL COMMENT '总佣金，单位：分',
  `type` tinyint NOT NULL COMMENT '提现类型',
  `name` varchar(64) NOT NULL COMMENT '真实姓名',
  `account_no` varchar(128) NOT NULL COMMENT '账号',
  `account_qr_code_url` varchar(512) DEFAULT NULL COMMENT '收款码',
  `bank_name` varchar(128) DEFAULT NULL COMMENT '银行名称',
  `bank_address` varchar(256) DEFAULT NULL COMMENT '开户地址',
  `status` tinyint NOT NULL COMMENT '状态',
  `audit_reason` varchar(500) DEFAULT NULL COMMENT '审核驳回原因',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` bigint DEFAULT NULL COMMENT '审核人',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地区代理提现表';

-- 插入菜单权限数据
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(5700, '地区代理', '', 1, 10, 2072, 'regional-agent', 'ep:location', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5701, '地区代理管理', 'product:regional-agent:query', 2, 1, 5700, 'agent', 'ep:user', 'mall/trade/regionalAgent/index', 'RegionalAgent', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5702, '地区代理查询', 'product:regional-agent:query', 3, 1, 5701, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5703, '地区代理创建', 'product:regional-agent:create', 3, 2, 5701, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5704, '地区代理更新', 'product:regional-agent:update', 3, 3, 5701, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5705, '地区代理删除', 'product:regional-agent:delete', 3, 4, 5701, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5706, '地区代理导出', 'product:regional-agent:export', 3, 5, 5701, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5707, '地区代理审核', 'product:regional-agent:approve', 3, 6, 5701, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5708, '代理记录管理', 'product:regional-agent-record:query', 2, 2, 5700, 'record', 'ep:document', 'mall/trade/regionalAgentRecord/index', 'RegionalAgentRecord', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5709, '代理记录查询', 'product:regional-agent-record:query', 3, 1, 5708, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5710, '代理记录导出', 'product:regional-agent-record:export', 3, 2, 5708, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5711, '代理提现管理', 'product:regional-agent-withdraw:query', 2, 3, 5700, 'withdraw', 'ep:money', 'mall/trade/regionalAgentWithdraw/index', 'RegionalAgentWithdraw', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5712, '代理提现查询', 'product:regional-agent-withdraw:query', 3, 1, 5711, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5713, '代理提现创建', 'product:regional-agent-withdraw:create', 3, 2, 5711, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5714, '代理提现导出', 'product:regional-agent-withdraw:export', 3, 3, 5711, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5715, '代理提现审核', 'product:regional-agent-withdraw:approve', 3, 4, 5711, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5716, '代理申请管理', 'product:regional-agent:apply', 2, 4, 5700, 'apply', 'ep:edit-pen', 'mall/trade/regionalAgentApply/index', 'RegionalAgentApply', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),

(5717, '代理申请查询', 'product:regional-agent:apply', 3, 1, 5716, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(5718, '代理申请创建', 'product:regional-agent:apply', 3, 2, 5716, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

-- 插入字典类型
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(600, '地区代理状态', 'regional_agent_status', 0, '地区代理状态', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(601, '地区代理记录业务类型', 'regional_agent_record_biz_type', 0, '地区代理记录业务类型', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(602, '地区代理记录状态', 'regional_agent_record_status', 0, '地区代理记录状态', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(603, '地区代理提现类型', 'regional_agent_withdraw_type', 0, '地区代理提现类型', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(604, '地区代理提现状态', 'regional_agent_withdraw_status', 0, '地区代理提现状态', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

-- 插入字典数据
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(6000, 1, '申请中', '0', 'regional_agent_status', 0, 'info', '', '申请中', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6001, 2, '已通过', '1', 'regional_agent_status', 0, 'success', '', '已通过', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6002, 3, '已拒绝', '2', 'regional_agent_status', 0, 'danger', '', '已拒绝', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6003, 4, '已禁用', '3', 'regional_agent_status', 0, 'warning', '', '已禁用', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6010, 1, '订单', '1', 'regional_agent_record_biz_type', 0, 'primary', '', '订单', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6011, 2, '提现', '2', 'regional_agent_record_biz_type', 0, 'warning', '', '提现', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6012, 3, '退款', '3', 'regional_agent_record_biz_type', 0, 'danger', '', '退款', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6013, 4, '手动调整', '4', 'regional_agent_record_biz_type', 0, 'info', '', '手动调整', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6020, 1, '待结算', '0', 'regional_agent_record_status', 0, 'info', '', '待结算', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6021, 2, '已结算', '1', 'regional_agent_record_status', 0, 'success', '', '已结算', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6022, 3, '已失效', '2', 'regional_agent_record_status', 0, 'danger', '', '已失效', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6030, 1, '钱包', '1', 'regional_agent_withdraw_type', 0, 'primary', '', '钱包', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6031, 2, '银行卡', '2', 'regional_agent_withdraw_type', 0, 'success', '', '银行卡', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6032, 3, '微信', '3', 'regional_agent_withdraw_type', 0, 'warning', '', '微信', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6033, 4, '支付宝', '4', 'regional_agent_withdraw_type', 0, 'info', '', '支付宝', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6040, 1, '审核中', '1', 'regional_agent_withdraw_status', 0, 'info', '', '审核中', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6041, 2, '审核通过', '2', 'regional_agent_withdraw_status', 0, 'success', '', '审核通过', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6042, 3, '审核不通过', '3', 'regional_agent_withdraw_status', 0, 'danger', '', '审核不通过', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6043, 4, '提现成功', '4', 'regional_agent_withdraw_status', 0, 'success', '', '提现成功', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
(6044, 5, '提现失败', '5', 'regional_agent_withdraw_status', 0, 'danger', '', '提现失败', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

-- 插入定时任务
INSERT INTO `infra_job` (`id`, `name`, `status`, `handler_name`, `handler_param`, `cron_expression`, `retry_count`, `retry_interval`, `monitor_timeout`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
(26, '地区代理佣金解冻 Job', 1, 'regionalAgentUnfreezeJob', '', '0 */10 * * * ?', 3, 0, 0, '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');