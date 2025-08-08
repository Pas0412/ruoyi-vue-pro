package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "用户 APP - 地区代理佣金记录月度汇总 Response VO")
@Data
public class AppTradeAreaAgentRecordMonthlySummaryRespVO {

    @Schema(description = "年份", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024")
    private Integer year;

    @Schema(description = "月度数据列表")
    private List<MonthlyData> monthlyDataList;

    @Data
    @Schema(description = "月度数据")
    public static class MonthlyData {

        @Schema(description = "月份", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer month;

        @Schema(description = "佣金金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "500")
        private Integer price;

        @Schema(description = "佣金记录数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
        private Integer count;

    }

}