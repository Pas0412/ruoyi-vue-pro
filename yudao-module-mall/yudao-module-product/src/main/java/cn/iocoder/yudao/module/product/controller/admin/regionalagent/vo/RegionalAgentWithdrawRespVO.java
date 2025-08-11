package cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 地区代理提现 Response VO")
@Data
public class RegionalAgentWithdrawRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "提现金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer price;

    @Schema(description = "手续费", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer feePrice;

    @Schema(description = "总佣金", requiredMode = Schema.RequiredMode.REQUIRED, example = "1010")
    private Integer totalPrice;

    @Schema(description = "提现类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "真实姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "6222021234567890")
    private String accountNo;

    @Schema(description = "收款码", example = "https://example.com/qrcode.jpg")
    private String accountQrCodeUrl;

    @Schema(description = "银行名称", example = "中国工商银行")
    private String bankName;

    @Schema(description = "开户地址", example = "北京市朝阳区")
    private String bankAddress;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "审核驳回原因", example = "信息不完整")
    private String auditReason;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人")
    private Long auditUserId;

    @Schema(description = "备注", example = "提现申请")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}