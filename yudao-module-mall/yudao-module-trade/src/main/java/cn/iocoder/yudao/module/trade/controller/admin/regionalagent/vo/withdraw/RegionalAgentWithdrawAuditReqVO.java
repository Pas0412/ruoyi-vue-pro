package cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.withdraw;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentWithdrawStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 地区代理提现审核 Request VO")
@Data
@ToString(callSuper = true)
public class RegionalAgentWithdrawAuditReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7161")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "审核状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "审核状态不能为空")
    @InEnum(value = RegionalAgentWithdrawStatusEnum.class, message = "审核状态必须是 {value}")
    private Integer status;

    @Schema(description = "审核原因", example = "不对")
    private String auditReason;

}