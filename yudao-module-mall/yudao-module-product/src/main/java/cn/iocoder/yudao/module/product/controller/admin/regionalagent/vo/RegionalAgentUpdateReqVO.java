package cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 地区代理更新 Request VO")
@Data
public class RegionalAgentUpdateReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110100")
    @NotNull(message = "地区编号不能为空")
    private Integer areaId;

    @Schema(description = "地区类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    @NotNull(message = "地区类型不能为空")
    private Integer areaType;

    @Schema(description = "地区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京市")
    @NotNull(message = "地区名称不能为空")
    private String areaName;

    @Schema(description = "审核备注", example = "审核通过")
    private String auditRemark;

}