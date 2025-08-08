package cn.iocoder.yudao.module.trade.enums.areaagent;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理佣金记录状态枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum AreaAgentRecordStatusEnum implements IntArrayValuable {

    SETTLEMENT(0, "待结算"),
    SETTLED(1, "已结算"),
    CANCELED(2, "已取消");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AreaAgentRecordStatusEnum::getStatus).toArray();

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static AreaAgentRecordStatusEnum valueOf(Integer status) {
        return Arrays.stream(values())
                .filter(item -> item.getStatus().equals(status))
                .findFirst()
                .orElse(null);
    }

}