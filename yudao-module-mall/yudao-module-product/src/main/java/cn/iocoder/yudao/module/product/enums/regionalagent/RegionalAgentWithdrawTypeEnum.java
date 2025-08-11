package cn.iocoder.yudao.module.product.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理提现类型枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum RegionalAgentWithdrawTypeEnum implements ArrayValuable<Integer> {

    WALLET(1, "钱包"),
    BANK(2, "银行卡"),
    WECHAT(3, "微信"),
    ALIPAY(4, "支付宝");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RegionalAgentWithdrawTypeEnum::getType).toArray(Integer[]::new);

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 类型名
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}