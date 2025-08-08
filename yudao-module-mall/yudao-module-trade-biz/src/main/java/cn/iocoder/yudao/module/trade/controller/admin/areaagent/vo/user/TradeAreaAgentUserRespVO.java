package cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 地区代理用户 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ExcelIgnoreUnannotated
public class TradeAreaAgentUserRespVO extends TradeAreaAgentUserBaseVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "成为代理时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("成为代理时间")
    private LocalDateTime agentTime;

    @Schema(description = "可用佣金（分）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @ExcelProperty("可用佣金（分）")
    private Integer commissionPrice;

    @Schema(description = "冻结佣金（分）", requiredMode = Schema.RequiredMode.REQUIRED, example = "500")
    @ExcelProperty("冻结佣金（分）")
    private Integer frozenPrice;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}