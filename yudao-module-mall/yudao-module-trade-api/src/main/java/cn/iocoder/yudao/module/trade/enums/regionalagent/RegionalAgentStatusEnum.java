package cn.iocoder.yudao.module.trade.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理状态枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum RegionalAgentStatusEnum implements ArrayValuable<Integer> {

    AUDITING(0, "待审核"),
    APPROVED(1, "已通过"),
    REJECTED(2, "已拒绝"),
    DISABLED(3, "已禁用"),
    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RegionalAgentStatusEnum::getStatus).toArray(Integer[]::new);

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 名字
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}