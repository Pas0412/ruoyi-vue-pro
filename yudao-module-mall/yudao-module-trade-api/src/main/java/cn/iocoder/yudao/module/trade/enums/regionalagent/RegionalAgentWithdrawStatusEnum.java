package cn.iocoder.yudao.module.trade.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
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
public enum RegionalAgentWithdrawStatusEnum implements ArrayValuable<Integer> {

    AUDITING(0, "审核中"),
    AUDIT_SUCCESS(10, "审核通过"),
    WITHDRAW_SUCCESS(11, "提现成功"),
    AUDIT_FAIL(20, "审核不通过"),
    WITHDRAW_FAIL(21, "提现失败"),
    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RegionalAgentWithdrawStatusEnum::getStatus).toArray(Integer[]::new);

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