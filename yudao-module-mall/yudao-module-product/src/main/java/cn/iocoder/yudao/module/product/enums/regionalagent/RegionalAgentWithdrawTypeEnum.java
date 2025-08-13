package cn.iocoder.yudao.module.product.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理提现类型枚举
 *
 * @author 芋道源码
 */
@Getter
public enum RegionalAgentWithdrawTypeEnum implements ArrayValuable<Integer> {

    WALLET(1, "钱包"),
    BANK(2, "银行卡"),
    WECHAT(3, "微信"),
    ALIPAY(4, "支付宝");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 类型名
     */
    private final String name;

    RegionalAgentWithdrawTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RegionalAgentWithdrawTypeEnum::getType).toArray(Integer[]::new);


    @Override
    public Integer[] array() {
        return ARRAYS;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}