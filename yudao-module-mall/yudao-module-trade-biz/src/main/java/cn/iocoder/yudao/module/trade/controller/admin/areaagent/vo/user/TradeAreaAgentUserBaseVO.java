package cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

/**
 * 地区代理用户 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TradeAreaAgentUserBaseVO {

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "代理地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    @NotNull(message = "代理地区编号不能为空")
    private Integer areaId;

    @Schema(description = "代理等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "代理等级不能为空")
    private Integer areaLevel;

    @Schema(description = "地区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京市")
    @NotBlank(message = "地区名称不能为空")
    private String areaName;

    @Schema(description = "上级地区编号", example = "100000")
    private Integer parentAreaId;

    @Schema(description = "代理资格启用状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "代理资格启用状态不能为空")
    private Boolean enabled;

}