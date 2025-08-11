package cn.iocoder.yudao.module.product.controller.app.regionalagent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户 APP - 地区代理提现 Response VO
 *
 * @author 芋道源码
 */
@Schema(description = "用户 APP - 地区代理提现 Response VO")
@Data
public class AppRegionalAgentWithdrawRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "提现金额，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer price;

    @Schema(description = "手续费，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
    private Integer feePrice;

    @Schema(description = "总佣金，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "5000")
    private Integer totalPrice;

    @Schema(description = "提现类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "真实姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "6222021234567890")
    private String accountNo;

    @Schema(description = "收款码", example = "https://example.com/qrcode.jpg")
    private String accountQrCodeUrl;

    @Schema(description = "银行名称", example = "中国银行")
    private String bankName;

    @Schema(description = "开户地址", example = "北京市朝阳区")
    private String bankAddress;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "审核驳回原因", example = "信息不完整")
    private String auditReason;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人", example = "1024")
    private Long auditUserId;

    @Schema(description = "备注", example = "提现申请")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}