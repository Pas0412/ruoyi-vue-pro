package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 地区代理用户统计 Response VO")
@Data
public class AppTradeAreaAgentUserSummaryRespVO {

    @Schema(description = "总佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer totalPrice;

    @Schema(description = "可用佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "800")
    private Integer availablePrice;

    @Schema(description = "冻结佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    private Integer frozenPrice;

    @Schema(description = "今日佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
    private Integer todayPrice;

    @Schema(description = "昨日佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "60")
    private Integer yesterdayPrice;

    @Schema(description = "本月佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "500")
    private Integer thisMonthPrice;

    @Schema(description = "上月佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "400")
    private Integer lastMonthPrice;

    @Schema(description = "下级代理数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer childAgentCount;

    @Schema(description = "直接下级数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer directChildCount;

    @Schema(description = "间接下级数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer indirectChildCount;

}