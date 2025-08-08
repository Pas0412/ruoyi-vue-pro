package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.user;

import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 地区代理用户下级 Response VO")
@Data
public class AppTradeAreaAgentUserChildRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long userId;

    @Schema(description = "用户昵称", example = "张三")
    private String nickname;

    @Schema(description = "用户头像", example = "https://www.iocoder.cn/xx.png")
    private String avatar;

    @Schema(description = "代理地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Long agentAreaId;

    @Schema(description = "代理等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private AreaAgentLevelEnum agentLevel;

    @Schema(description = "地区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京市")
    private String areaName;

    @Schema(description = "代理资格启用状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean agentEnabled;

    @Schema(description = "成为代理时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime agentTime;

    @Schema(description = "总佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer totalPrice;

    @Schema(description = "本月佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
    private Integer thisMonthPrice;

    @Schema(description = "下级代理数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer childAgentCount;

}