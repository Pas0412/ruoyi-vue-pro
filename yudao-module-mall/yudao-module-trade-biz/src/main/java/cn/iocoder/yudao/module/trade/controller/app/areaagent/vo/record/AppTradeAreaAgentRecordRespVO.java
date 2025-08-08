package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.record;

import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentLevelEnum;
import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentRecordStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 地区代理佣金记录 Response VO")
@Data
public class AppTradeAreaAgentRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "代理用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long agentUserId;

    @Schema(description = "代理地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Long agentAreaId;

    @Schema(description = "代理等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private AreaAgentLevelEnum agentLevel;

    @Schema(description = "业务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3072")
    private String bizId;

    @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private AreaAgentRecordBizTypeEnum bizType;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "订单佣金")
    private String title;

    @Schema(description = "佣金金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer price;

    @Schema(description = "当前总佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer totalPrice;

    @Schema(description = "说明", example = "订单完成获得佣金")
    private String description;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private AreaAgentRecordStatusEnum status;

    @Schema(description = "冻结时间")
    private LocalDateTime frozenTime;

    @Schema(description = "解冻时间")
    private LocalDateTime unfreezeTime;

    @Schema(description = "来源用户编号", example = "4096")
    private Long sourceUserId;

    @Schema(description = "来源用户昵称", example = "李四")
    private String sourceUserNickname;

    @Schema(description = "订单收货地区编号", example = "110100")
    private Long orderAreaId;

    @Schema(description = "订单收货地区名称", example = "北京市东城区")
    private String orderAreaName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}