package cn.iocoder.yudao.module.product.controller.app.regionalagent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户 APP - 地区代理 Response VO
 *
 * @author 芋道源码
 */
@Schema(description = "用户 APP - 地区代理 Response VO")
@Data
public class AppRegionalAgentRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer areaId;

    @Schema(description = "地区类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer areaType;

    @Schema(description = "地区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京市")
    private String areaName;

    @Schema(description = "代理状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "申请时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime applyTime;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人", example = "1024")
    private Long auditUserId;

    @Schema(description = "审核备注", example = "审核通过")
    private String auditRemark;

    @Schema(description = "可用佣金，单位：元", requiredMode = Schema.RequiredMode.REQUIRED, example = "10.00")
    private BigDecimal brokeragePrice;

    @Schema(description = "冻结佣金，单位：元", requiredMode = Schema.RequiredMode.REQUIRED, example = "5.00")
    private BigDecimal frozenBrokeragePrice;

    @Schema(description = "成为代理时间")
    private LocalDateTime agentTime;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}