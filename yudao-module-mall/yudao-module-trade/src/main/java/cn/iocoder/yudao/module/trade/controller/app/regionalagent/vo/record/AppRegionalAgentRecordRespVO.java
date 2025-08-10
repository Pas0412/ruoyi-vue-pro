package cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 App - 地区代理佣金记录 Response VO")
@Data
public class AppRegionalAgentRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28896")
    private Long id;

    @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer bizType;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "订单佣金")
    private String title;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "9000")
    private Integer price;

    @Schema(description = "当前总佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "9000")
    private Integer totalPrice;

    @Schema(description = "说明", example = "订单佣金")
    private String description;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "解冻时间")
    private LocalDateTime unfreezeTime;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}