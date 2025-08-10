package cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.agent;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "用户 App - 地区代理申请 Request VO")
@Data
public class AppRegionalAgentApplyReqVO {

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

}