package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 地区代理佣金记录汇总 Response VO")
@Data
public class AppTradeAreaAgentRecordSummaryRespVO {

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

    @Schema(description = "本周佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "300")
    private Integer thisWeekPrice;

    @Schema(description = "上周佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "250")
    private Integer lastWeekPrice;

    @Schema(description = "本月佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "500")
    private Integer thisMonthPrice;

    @Schema(description = "上月佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "400")
    private Integer lastMonthPrice;

    @Schema(description = "本年佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "3000")
    private Integer thisYearPrice;

    @Schema(description = "去年佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "2500")
    private Integer lastYearPrice;

}