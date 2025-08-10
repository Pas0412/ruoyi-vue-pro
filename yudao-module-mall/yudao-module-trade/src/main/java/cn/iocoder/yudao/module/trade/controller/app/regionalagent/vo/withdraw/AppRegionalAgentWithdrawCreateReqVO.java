package cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.withdraw;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "用户 App - 地区代理提现申请 Request VO")
@Data
public class AppRegionalAgentWithdrawCreateReqVO {

    @Schema(description = "提现金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "18781")
    @NotNull(message = "提现金额不能为空")
    private Integer price;

    @Schema(description = "提现类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "提现类型不能为空")
    @InEnum(value = BrokerageWithdrawTypeEnum.class, message = "提现类型必须是 {value}")
    private Integer type;

    @Schema(description = "真实姓名", example = "赵六")
    private String userName;

    @Schema(description = "收款账号", example = "88677912132")
    private String userAccount;

    @Schema(description = "银行名称", example = "1")
    private String bankName;

    @Schema(description = "开户地址", example = "海淀支行")
    private String bankAddress;

    @Schema(description = "收款码", example = "https://www.iocoder.cn/xxx.png")
    private String accountQrCode;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}