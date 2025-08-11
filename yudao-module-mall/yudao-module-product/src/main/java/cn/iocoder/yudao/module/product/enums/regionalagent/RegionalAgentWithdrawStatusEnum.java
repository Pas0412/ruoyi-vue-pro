package cn.iocoder.yudao.module.product.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理提现状态枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum RegionalAgentWithdrawStatusEnum implements IntArrayValuable {

    AUDITING(0, "审核中"),
    AUDIT_SUCCESS(10, "审核通过"),
    AUDIT_FAIL(20, "审核不通过"),
    WITHDRAW_SUCCESS(11, "提现成功"),
    WITHDRAW_FAIL(21, "提现失败");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(RegionalAgentWithdrawStatusEnum::getStatus).toArray();

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