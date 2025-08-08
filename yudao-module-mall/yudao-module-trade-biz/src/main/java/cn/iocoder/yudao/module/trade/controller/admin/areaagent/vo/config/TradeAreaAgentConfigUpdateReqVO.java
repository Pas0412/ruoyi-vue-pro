package cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 地区代理配置更新 Request VO")
@Data
public class TradeAreaAgentConfigUpdateReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "代理等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "代理等级不能为空")
    private Integer areaLevel;

    @Schema(description = "佣金比例（%）", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "佣金比例不能为空")
    @Min(value = 0, message = "佣金比例不能小于0")
    @Max(value = 100, message = "佣金比例不能大于100")
    private Integer commissionPercent;

    @Schema(description = "佣金冻结天数", requiredMode = Schema.RequiredMode.REQUIRED, example = "7")
    @NotNull(message = "佣金冻结天数不能为空")
    @Min(value = 0, message = "佣金冻结天数不能小于0")
    private Integer frozenDays;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;

}