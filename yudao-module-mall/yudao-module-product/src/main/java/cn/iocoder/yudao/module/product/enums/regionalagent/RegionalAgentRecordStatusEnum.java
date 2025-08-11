package cn.iocoder.yudao.module.product.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理记录状态枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum RegionalAgentRecordStatusEnum implements ArrayValuable<Integer> {

    WAIT_SETTLEMENT(0, "待结算"),
    SETTLEMENT(1, "已结算"),
    CANCEL(2, "已失效");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RegionalAgentRecordStatusEnum::getStatus).toArray(Integer[]::new);

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}