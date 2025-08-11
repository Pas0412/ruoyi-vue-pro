-- 地区代理申请页面菜单配置
-- 可以直接执行此SQL文件来添加代理申请相关菜单

INSERT INTO system_menu (
    id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted
) VALUES 
-- 代理申请管理主菜单
(5716, '代理申请管理', 'product:regional-agent:apply', 2, 4, 5700, 'apply', 'ep:edit-pen', 'mall/trade/regionalAgentApply/index', 'RegionalAgentApply', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
-- 代理申请查询权限
(5717, '代理申请查询', 'product:regional-agent:apply', 3, 1, 5716, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0'),
-- 代理申请创建权限
(5718, '代理申请创建', 'product:regional-agent:apply', 3, 2, 5716, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-01 00:00:00', '1', '2024-01-01 00:00:00', b'0');

-- 说明：
-- 2716: 代理申请管理页面，路由为 product/regionalagent/apply
-- 2717: 代理申请查询权限
-- 2718: 代理申请创建权限
-- parent_id 2700 对应地区代理模块
-- 如果需要删除这些菜单，可以执行：
-- DELETE FROM system_menu WHERE id IN (2716, 2717, 2718);