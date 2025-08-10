package cn.iocoder.yudao.module.trade.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理级别枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum RegionalAgentLevelEnum implements ArrayValuable<Integer> {

    PROVINCE(1, "省级代理"),
    CITY(2, "市级代理"),
    DISTRICT(3, "县级代理"),
    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RegionalAgentLevelEnum::getLevel).toArray(Integer[]::new);

    /**
     * 级别
     */
    private final Integer level;
    /**
     * 名字
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}