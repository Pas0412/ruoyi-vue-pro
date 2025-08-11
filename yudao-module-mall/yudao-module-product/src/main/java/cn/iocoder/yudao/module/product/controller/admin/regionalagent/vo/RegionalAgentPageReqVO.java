package cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
lombok.Data;
lombok.EqualsAndHashCode;
lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 地区代理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegionalAgentPageReqVO extends PageParam {

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "地区编号")
    private Integer areaId;

    @Schema(description = "地区类型")
    private Integer areaType;

    @Schema(description = "地区名称")
    private String areaName;

    @Schema(description = "代理状态")
    private Integer status;

    @Schema(description = "申请时间")
    private LocalDateTime[] applyTime;

    @Schema(description = "审核时间")
    private LocalDateTime[] auditTime;

}