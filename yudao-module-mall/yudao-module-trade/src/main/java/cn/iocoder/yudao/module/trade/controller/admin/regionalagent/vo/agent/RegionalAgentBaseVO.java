package cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.agent;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentLevelEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 地区代理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RegionalAgentBaseVO {

    @Schema(description = "省编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    @NotNull(message = "省编号不能为空")
    private Integer provinceId;

    @Schema(description = "市编号", example = "110100")
    private Integer cityId;

    @Schema(description = "县编号", example = "110101")
    private Integer areaId;

    @Schema(description = "代理级别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "代理级别不能为空")
    @InEnum(value = RegionalAgentLevelEnum.class, message = "代理级别必须是 {value}")
    private Integer level;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    @InEnum(value = RegionalAgentStatusEnum.class, message = "状态必须是 {value}")
    private Integer status;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime applyTime;

    @Schema(description = "审核时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime auditTime;

    @Schema(description = "审核原因", example = "符合代理条件")
    private String auditReason;

    @Schema(description = "可用佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "11089")
    @NotNull(message = "可用佣金不能为空")
    private Integer price;

    @Schema(description = "冻结佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "30916")
    @NotNull(message = "冻结佣金不能为空")
    private Integer frozenPrice;

}