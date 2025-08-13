package cn.iocoder.yudao.module.product.enums.comment;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.Getter;

import java.util.Arrays;

/**
 * 商品评论的审批状态枚举
 *
 * @author 芋道源码
 */
@Getter
public enum ProductCommentAuditStatusEnum implements ArrayValuable<Integer> {

    NONE(0, "待审核"),
    APPROVE(1, "审批通过"),
    REJECT(2, "审批不通过");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(item -> item.getStatus()).toArray(Integer[]::new);

    /**
     * 审批状态
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    ProductCommentAuditStatusEnum(Integer status, String name) {
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

}
