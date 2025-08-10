package cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.record;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentLevelEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentRecordStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 地区代理佣金记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RegionalAgentRecordBaseVO {

    @Schema(description = "代理编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "代理编号不能为空")
    private Long agentId;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25973")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "业务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "业务编号不能为空")
    private String bizId;

    @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "业务类型不能为空")
    @InEnum(value = RegionalAgentRecordBizTypeEnum.class, message = "业务类型必须是 {value}")
    private Integer bizType;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "订单佣金")
    @NotEmpty(message = "标题不能为空")
    private String title;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "9000")
    @NotNull(message = "金额不能为空")
    private Integer price;

    @Schema(description = "当前总佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "9000")
    @NotNull(message = "当前总佣金不能为空")
    private Integer totalPrice;

    @Schema(description = "说明", example = "订单佣金")
    private String description;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    @InEnum(value = RegionalAgentRecordStatusEnum.class, message = "状态必须是 {value}")
    private Integer status;

    @Schema(description = "冻结天数", requiredMode = Schema.RequiredMode.REQUIRED, example = "7")
    @NotNull(message = "冻结天数不能为空")
    private Integer frozenDays;

    @Schema(description = "解冻时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime unfreezeTime;

    @Schema(description = "代理级别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "代理级别不能为空")
    @InEnum(value = RegionalAgentLevelEnum.class, message = "代理级别必须是 {value}")
    private Integer agentLevel;

    @Schema(description = "来源用户编号", example = "1")
    private Long sourceUserId;

    @Schema(description = "订单编号", example = "1")
    private Long orderId;

    @Schema(description = "省编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    @NotNull(message = "省编号不能为空")
    private Integer provinceId;

    @Schema(description = "市编号", example = "110100")
    private Integer cityId;

    @Schema(description = "县编号", example = "110101")
    private Integer areaId;

}