package cn.iocoder.yudao.module.product.controller.app.regionalagent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 用户 APP - 地区代理提现创建 Request VO
 *
 * @author 芋道源码
 */
@Schema(description = "用户 APP - 地区代理提现创建 Request VO")
@Data
public class AppRegionalAgentWithdrawCreateReqVO {

    @Schema(description = "提现金额，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @NotNull(message = "提现金额不能为空")
    @Positive(message = "提现金额必须大于零")
    private Integer price;

    @Schema(description = "提现类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "提现类型不能为空")
    private Integer type;

    @Schema(description = "真实姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "真实姓名不能为空")
    private String name;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "6222021234567890")
    @NotNull(message = "账号不能为空")
    private String accountNo;

    @Schema(description = "收款码", example = "https://example.com/qrcode.jpg")
    private String accountQrCodeUrl;

    @Schema(description = "银行名称", example = "中国银行")
    private String bankName;

    @Schema(description = "开户地址", example = "北京市朝阳区")
    private String bankAddress;

    @Schema(description = "备注", example = "提现申请")
    private String remark;

}