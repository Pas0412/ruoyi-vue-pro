package cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.agent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 App - 地区代理信息 Response VO")
@Data
public class AppRegionalAgentRespVO {

    @Schema(description = "代理编号", example = "1")
    private Long id;

    @Schema(description = "是否是地区代理", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean isAgent;

    @Schema(description = "代理级别", example = "1")
    private Integer level;

    @Schema(description = "代理状态", example = "2")
    private Integer status;

    @Schema(description = "可用的佣金，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "2408")
    private Integer brokeragePrice;

    @Schema(description = "冻结的佣金，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "234")
    private Integer frozenPrice;

    // ========== 地区信息 ==========

    @Schema(description = "省编号", example = "110000")
    private Integer provinceId;
    @Schema(description = "市编号", example = "110100")
    private Integer cityId;
    @Schema(description = "县编号", example = "110101")
    private Integer areaId;

    @Schema(description = "省名称", example = "北京市")
    private String provinceName;
    @Schema(description = "市名称", example = "北京市")
    private String cityName;
    @Schema(description = "县名称", example = "东城区")
    private String areaName;

}