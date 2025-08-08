package cn.iocoder.yudao.module.trade.enums.areaagent;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理佣金记录业务类型枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum AreaAgentRecordBizTypeEnum implements IntArrayValuable {

    ORDER(1, "订单"),
    WITHDRAW(2, "提现");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AreaAgentRecordBizTypeEnum::getType).toArray();

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static AreaAgentRecordBizTypeEnum valueOf(Integer type) {
        return Arrays.stream(values())
                .filter(item -> item.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

}