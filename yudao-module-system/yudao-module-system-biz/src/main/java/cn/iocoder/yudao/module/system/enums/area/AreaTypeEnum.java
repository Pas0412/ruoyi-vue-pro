package cn.iocoder.yudao.module.system.enums.area;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区类型枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum AreaTypeEnum implements IntArrayValuable {

    COUNTRY(1, "国家"),
    PROVINCE(2, "省"),
    CITY(3, "市"),
    DISTRICT(4, "县区");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AreaTypeEnum::getType).toArray();

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

    public static AreaTypeEnum valueOf(Integer type) {
        return Arrays.stream(values())
                .filter(item -> item.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

}