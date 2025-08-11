package cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 地区代理记录 Response VO")
@Data
public class RegionalAgentRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "业务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "O202301010001")
    private String bizId;

    @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer bizType;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "订单佣金")
    private String title;

    @Schema(description = "说明", example = "订单编号：O202301010001")
    private String description;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer price;

    @Schema(description = "当前总佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer totalPrice;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "冻结时间")
    private LocalDateTime frozenTime;

    @Schema(description = "解冻时间")
    private LocalDateTime unfreezeTime;

    @Schema(description = "来源用户等级")
    private Integer sourceUserLevel;

    @Schema(description = "来源用户编号")
    private Long sourceUserId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}