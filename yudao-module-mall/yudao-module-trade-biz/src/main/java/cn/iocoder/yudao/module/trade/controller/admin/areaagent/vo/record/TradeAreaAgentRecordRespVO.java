package cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.record;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 地区代理佣金记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TradeAreaAgentRecordRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "代理用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("代理用户编号")
    private Long userId;

    @Schema(description = "代理地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    @ExcelProperty("代理地区编号")
    private Integer areaId;

    @Schema(description = "代理等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("代理等级")
    private Integer areaLevel;

    @Schema(description = "业务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "ORDER_001")
    @ExcelProperty("业务编号")
    private String bizId;

    @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("业务类型")
    private Integer bizType;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "订单佣金")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "佣金金额（分）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @ExcelProperty("佣金金额（分）")
    private Integer price;

    @Schema(description = "当前总佣金（分）", requiredMode = Schema.RequiredMode.REQUIRED, example = "5000")
    @ExcelProperty("当前总佣金（分）")
    private Integer totalPrice;

    @Schema(description = "说明", example = "订单 ORDER_001 地区代理佣金")
    @ExcelProperty("说明")
    private String description;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "冻结时间（天）", requiredMode = Schema.RequiredMode.REQUIRED, example = "7")
    @ExcelProperty("冻结时间（天）")
    private Integer frozenDays;

    @Schema(description = "解冻时间")
    @ExcelProperty("解冻时间")
    private LocalDateTime unfreezeTime;

    @Schema(description = "来源用户编号", example = "2048")
    @ExcelProperty("来源用户编号")
    private Long sourceUserId;

    @Schema(description = "订单收货地区编号", example = "110101")
    @ExcelProperty("订单收货地区编号")
    private Integer orderAreaId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}