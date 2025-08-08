package cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 地区代理配置 Response VO")
@Data
public class TradeAreaAgentConfigRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "代理等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer areaLevel;

    @Schema(description = "佣金比例（%）", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer commissionPercent;

    @Schema(description = "佣金冻结天数", requiredMode = Schema.RequiredMode.REQUIRED, example = "7")
    private Integer frozenDays;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean enabled;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

}