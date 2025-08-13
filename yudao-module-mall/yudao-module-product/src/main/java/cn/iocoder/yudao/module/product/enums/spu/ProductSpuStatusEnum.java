package cn.iocoder.yudao.module.product.enums.spu;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.Getter;

import java.util.Arrays;

/**
 * 商品 SPU 状态
 *
 * @author 芋道源码
 */
@Getter
public enum ProductSpuStatusEnum implements ArrayValuable<Integer> {

    RECYCLE(-1, "回收站"),
    DISABLE(0, "下架"),
    ENABLE(1, "上架");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(item -> item.getStatus()).toArray(Integer[]::new);

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    ProductSpuStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

    /**
     * 判断是否处于【上架】状态
     *
     * @param status 状态
     * @return 是否处于【上架】状态
     */
    public static boolean isEnable(Integer status) {
        return ENABLE.getStatus().equals(status);
    }

}
