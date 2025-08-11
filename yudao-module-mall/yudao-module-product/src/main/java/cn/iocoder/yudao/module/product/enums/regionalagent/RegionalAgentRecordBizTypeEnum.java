package cn.iocoder.yudao.module.product.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理记录业务类型枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum RegionalAgentRecordBizTypeEnum implements IntArrayValuable {

    ORDER(1, "订单"),
    WITHDRAW(2, "提现"),
    REFUND(3, "退款"),
    MANUAL(4, "手动调整");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(RegionalAgentRecordBizTypeEnum::getType).toArray();

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 类型名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}