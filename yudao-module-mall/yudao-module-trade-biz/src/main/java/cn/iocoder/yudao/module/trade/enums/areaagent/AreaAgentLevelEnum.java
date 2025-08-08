package cn.iocoder.yudao.module.trade.enums.areaagent;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理等级枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum AreaAgentLevelEnum implements IntArrayValuable {

    PROVINCE(2, "省级代理"),
    CITY(3, "市级代理"),
    DISTRICT(4, "县区级代理");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AreaAgentLevelEnum::getLevel).toArray();

    /**
     * 等级
     */
    private final Integer level;
    /**
     * 名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static AreaAgentLevelEnum valueOf(Integer level) {
        return Arrays.stream(values())
                .filter(item -> item.getLevel().equals(level))
                .findFirst()
                .orElse(null);
    }

}