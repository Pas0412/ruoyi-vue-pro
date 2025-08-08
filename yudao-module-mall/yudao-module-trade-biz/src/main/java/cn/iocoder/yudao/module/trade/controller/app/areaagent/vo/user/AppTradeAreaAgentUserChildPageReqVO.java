package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.user;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "用户 APP - 地区代理用户下级分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppTradeAreaAgentUserChildPageReqVO extends PageParam {

    @Schema(description = "用户编号", hidden = true)
    private Long userId;

    @Schema(description = "代理等级", example = "1")
    private AreaAgentLevelEnum agentLevel;

    @Schema(description = "地区名称", example = "北京")
    private String areaName;

    @Schema(description = "代理资格启用状态", example = "true")
    private Boolean agentEnabled;

}