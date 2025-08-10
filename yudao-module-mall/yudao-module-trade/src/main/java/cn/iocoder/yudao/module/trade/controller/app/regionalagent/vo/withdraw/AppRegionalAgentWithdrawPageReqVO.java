package cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.withdraw;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentWithdrawStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 App - 地区代理提现分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppRegionalAgentWithdrawPageReqVO extends PageParam {

    @Schema(description = "提现类型", example = "1")
    @InEnum(value = BrokerageWithdrawTypeEnum.class, message = "提现类型必须是 {value}")
    private Integer type;

    @Schema(description = "状态", example = "1")
    @InEnum(value = RegionalAgentWithdrawStatusEnum.class, message = "状态必须是 {value}")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}