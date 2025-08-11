package cn.iocoder.yudao.module.product.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
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
public enum RegionalAgentStatusEnum implements IntArrayValuable {

    APPLYING(0, "申请中"),
    APPROVED(1, "已通过"),
    REJECTED(2, "已拒绝"),
    DISABLED(3, "已禁用");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(RegionalAgentStatusEnum::getStatus).toArray();

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

    /**
     * 判断是否为申请中状态
     *
     * @param status 状态
     * @return 是否为申请中状态
     */
    public static boolean isApplying(Integer status) {
        return APPLYING.getStatus().equals(status);
    }

    /**
     * 判断是否为已通过状态
     *
     * @param status 状态
     * @return 是否为已通过状态
     */
    public static boolean isApproved(Integer status) {
        return APPROVED.getStatus().equals(status);
    }

}