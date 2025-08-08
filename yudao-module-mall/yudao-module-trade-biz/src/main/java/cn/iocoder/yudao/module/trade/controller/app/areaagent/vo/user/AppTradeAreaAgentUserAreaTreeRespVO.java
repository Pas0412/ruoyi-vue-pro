package cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.user;

import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "用户 APP - 地区代理用户地区树 Response VO")
@Data
public class AppTradeAreaAgentUserAreaTreeRespVO {

    @Schema(description = "地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Long areaId;

    @Schema(description = "地区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京市")
    private String areaName;

    @Schema(description = "地区类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer areaType;

    @Schema(description = "父级编号", example = "100000")
    private Long parentId;

    @Schema(description = "是否可申请", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean available;

    @Schema(description = "已被代理的等级", example = "1")
    private AreaAgentLevelEnum agentLevel;

    @Schema(description = "代理用户昵称", example = "张三")
    private String agentNickname;

    @Schema(description = "子地区列表")
    private List<AppTradeAreaAgentUserAreaTreeRespVO> children;

}