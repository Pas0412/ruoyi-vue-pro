package cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.withdraw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 地区代理提现 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegionalAgentWithdrawRespVO extends RegionalAgentWithdrawBaseVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7161")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== 用户信息 ==========

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    private String userNickname;

    @Schema(description = "用户头像", example = "https://www.iocoder.cn/xxx.png")
    private String userAvatar;

    // ========== 地区信息 ==========

    @Schema(description = "省名称", example = "北京市")
    private String provinceName;
    @Schema(description = "市名称", example = "北京市")
    private String cityName;
    @Schema(description = "县名称", example = "东城区")
    private String areaName;

    // ========== 代理信息 ==========

    @Schema(description = "代理级别", example = "1")
    private Integer agentLevel;

}