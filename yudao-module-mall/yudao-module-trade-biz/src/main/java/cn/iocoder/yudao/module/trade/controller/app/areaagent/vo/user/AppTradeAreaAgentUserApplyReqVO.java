package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "用户 APP - 地区代理用户申请 Request VO")
@Data
public class AppTradeAreaAgentUserApplyReqVO {

    @Schema(description = "代理地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    @NotNull(message = "代理地区编号不能为空")
    private Long areaId;

}