package cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.agent;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 地区代理 - 修改状态 Request VO")
@Data
@ToString(callSuper = true)
public class RegionalAgentUpdateStatusReqVO {

    @Schema(description = "代理编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "代理编号不能为空")
    private Long id;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态不能为空")
    @InEnum(value = RegionalAgentStatusEnum.class, message = "状态必须是 {value}")
    private Integer status;

}