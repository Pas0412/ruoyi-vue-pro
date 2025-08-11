package cn.iocoder.yudao.module.product.controller.app.regionalagent.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 用户 APP - 地区代理分页 Request VO
 *
 * @author 芋道源码
 */
@Schema(description = "用户 APP - 地区代理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppRegionalAgentPageReqVO extends PageParam {

    @Schema(description = "地区编号", example = "1024")
    private Integer areaId;

    @Schema(description = "地区类型", example = "1")
    private Integer areaType;

    @Schema(description = "地区名称", example = "北京市")
    private String areaName;

    @Schema(description = "代理状态", example = "1")
    private Integer status;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

    @Schema(description = "审核时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] auditTime;

}