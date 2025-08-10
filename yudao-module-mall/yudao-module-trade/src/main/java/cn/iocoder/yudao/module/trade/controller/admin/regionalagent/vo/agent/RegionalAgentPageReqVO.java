package cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.agent;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentLevelEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 地区代理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegionalAgentPageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "1")
    private Long userId;

    @Schema(description = "省编号", example = "110000")
    private Integer provinceId;

    @Schema(description = "市编号", example = "110100")
    private Integer cityId;

    @Schema(description = "县编号", example = "110101")
    private Integer districtId;

    @Schema(description = "代理级别", example = "1")
    @InEnum(value = RegionalAgentLevelEnum.class, message = "代理级别必须是 {value}")
    private Integer agentLevel;

    @Schema(description = "状态", example = "1")
    @InEnum(value = RegionalAgentStatusEnum.class, message = "状态必须是 {value}")
    private Integer status;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

    @Schema(description = "审核时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] auditTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}