package cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.agent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 地区代理 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegionalAgentRespVO extends RegionalAgentBaseVO {

    @Schema(description = "代理编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Long userId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== 用户信息 ==========

    @Schema(description = "用户头像", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn/xxx.png")
    private String avatar;
    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    private String nickname;

    // ========== 地区信息 ==========

    @Schema(description = "省名称", example = "北京市")
    private String provinceName;
    @Schema(description = "市名称", example = "北京市")
    private String cityName;
    @Schema(description = "县名称", example = "东城区")
    private String areaName;

    // ========== 代理信息 ==========

    @Schema(description = "代理订单数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Integer agentOrderCount;
    @Schema(description = "代理订单金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Integer agentOrderPrice;

    // ========== 提现信息 ==========

    @Schema(description = "已提现金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Integer withdrawPrice;
    @Schema(description = "已提现次数", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Integer withdrawCount;

}