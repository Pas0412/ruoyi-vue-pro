package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "用户 APP - 地区代理佣金记录趋势 Response VO")
@Data
public class AppTradeAreaAgentRecordTrendRespVO {

    @Schema(description = "趋势数据列表")
    private List<TrendData> trendDataList;

    @Data
    @Schema(description = "趋势数据")
    public static class TrendData {

        @Schema(description = "日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-01-01")
        private LocalDate date;

        @Schema(description = "佣金金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private Integer price;

        @Schema(description = "佣金记录数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
        private Integer count;

    }

}