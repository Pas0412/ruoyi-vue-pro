package cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.record;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 地区代理佣金记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TradeAreaAgentRecordPageReqVO extends PageParam {

    @Schema(description = "代理用户编号", example = "1024")
    private Long userId;

    @Schema(description = "代理地区编号", example = "110000")
    private Integer areaId;

    @Schema(description = "代理等级", example = "2")
    private Integer areaLevel;

    @Schema(description = "业务编号", example = "ORDER_001")
    private String bizId;

    @Schema(description = "业务类型", example = "1")
    private Integer bizType;

    @Schema(description = "标题", example = "订单佣金")
    private String title;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "来源用户编号", example = "2048")
    private Long sourceUserId;

    @Schema(description = "订单收货地区编号", example = "110101")
    private Integer orderAreaId;

    @Schema(description = "解冻时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] unfreezeTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}