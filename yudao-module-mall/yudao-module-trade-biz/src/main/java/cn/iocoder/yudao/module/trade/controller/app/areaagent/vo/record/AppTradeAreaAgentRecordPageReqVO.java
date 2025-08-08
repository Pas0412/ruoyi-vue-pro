package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.record;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentRecordStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 地区代理佣金记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppTradeAreaAgentRecordPageReqVO extends PageParam {

    @Schema(description = "代理用户编号", hidden = true)
    private Long agentUserId;

    @Schema(description = "业务类型", example = "1")
    private AreaAgentRecordBizTypeEnum bizType;

    @Schema(description = "状态", example = "1")
    private AreaAgentRecordStatusEnum status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}