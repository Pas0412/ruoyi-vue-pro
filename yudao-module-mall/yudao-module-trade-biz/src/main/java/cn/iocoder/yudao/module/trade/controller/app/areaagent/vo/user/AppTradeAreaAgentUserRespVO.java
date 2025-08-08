package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.user;

import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 地区代理用户 Response VO")
@Data
public class AppTradeAreaAgentUserRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long userId;

    @Schema(description = "代理地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Long agentAreaId;

    @Schema(description = "代理等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private AreaAgentLevelEnum agentLevel;

    @Schema(description = "地区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京市")
    private String areaName;

    @Schema(description = "上级地区编号", example = "100000")
    private Long parentAreaId;

    @Schema(description = "上级地区名称", example = "中国")
    private String parentAreaName;

    @Schema(description = "代理资格启用状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean agentEnabled;

    @Schema(description = "成为代理时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime agentTime;

    @Schema(description = "可用佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer availablePrice;

    @Schema(description = "冻结佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer frozenPrice;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}