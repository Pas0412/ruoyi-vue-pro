package cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 地区代理审核 Request VO")
@Data
public class RegionalAgentApproveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "代理状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "代理状态不能为空")
    private Integer status;

    @Schema(description = "审核备注", example = "审核通过")
    private String auditRemark;

}