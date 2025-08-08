package cn.iocoder.yudao.module.trade.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Trade 错误码枚举类
 *
 * trade 系统，使用 1-011-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 地区代理用户 1-011-001-000 ==========
    ErrorCode AREA_AGENT_USER_NOT_EXISTS = new ErrorCode(1_011_001_000, "地区代理用户不存在");
    ErrorCode AREA_AGENT_USER_EXISTS = new ErrorCode(1_011_001_001, "该用户已是地区代理");
    ErrorCode AREA_AGENT_AREA_EXISTS = new ErrorCode(1_011_001_002, "该地区已有代理");
    ErrorCode AREA_AGENT_USER_DISABLED = new ErrorCode(1_011_001_003, "地区代理用户已被禁用");
    ErrorCode AREA_AGENT_COMMISSION_NOT_ENOUGH = new ErrorCode(1_011_001_004, "地区代理佣金不足");

    // ========== 地区代理佣金记录 1-011-002-000 ==========
    ErrorCode AREA_AGENT_RECORD_NOT_EXISTS = new ErrorCode(1_011_002_000, "地区代理佣金记录不存在");
    ErrorCode AREA_AGENT_RECORD_STATUS_ERROR = new ErrorCode(1_011_002_001, "地区代理佣金记录状态错误");
    ErrorCode AREA_AGENT_RECORD_FROZEN = new ErrorCode(1_011_002_002, "地区代理佣金记录已冻结");
    ErrorCode AREA_AGENT_RECORD_UNFROZEN = new ErrorCode(1_011_002_003, "地区代理佣金记录已解冻");
    ErrorCode AREA_AGENT_RECORD_CANCELLED = new ErrorCode(1_011_002_004, "地区代理佣金记录已取消");

    // ========== 地区代理配置 1-011-003-000 ==========
    ErrorCode AREA_AGENT_CONFIG_NOT_EXISTS = new ErrorCode(1_011_003_000, "地区代理配置不存在");
    ErrorCode AREA_AGENT_CONFIG_DISABLED = new ErrorCode(1_011_003_001, "地区代理配置已禁用");
    ErrorCode AREA_AGENT_CONFIG_LEVEL_EXISTS = new ErrorCode(1_011_003_002, "该代理等级配置已存在");

}