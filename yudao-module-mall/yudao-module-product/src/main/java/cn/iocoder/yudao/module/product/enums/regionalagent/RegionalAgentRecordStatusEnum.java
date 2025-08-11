package cn.iocoder.yudao.module.product.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
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
public enum RegionalAgentRecordStatusEnum implements IntArrayValuable {

    WAIT_SETTLEMENT(0, "待结算"),
    SETTLEMENT(1, "已结算"),
    CANCEL(2, "已失效");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(RegionalAgentRecordStatusEnum::getStatus).toArray();

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}