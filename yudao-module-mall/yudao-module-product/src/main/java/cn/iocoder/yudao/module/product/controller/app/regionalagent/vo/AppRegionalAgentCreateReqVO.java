package cn.iocoder.yudao.module.product.controller.app.regionalagent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户 APP - 地区代理创建 Request VO
 *
 * @author 芋道源码
 */
@Schema(description = "用户 APP - 地区代理创建 Request VO")
@Data
public class AppRegionalAgentCreateReqVO {

    @Schema(description = "地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "地区编号不能为空")
    private Integer areaId;

    @Schema(description = "地区类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "地区类型不能为空")
    private Integer areaType;

    @Schema(description = "地区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京市")
    @NotNull(message = "地区名称不能为空")
    private String areaName;

}