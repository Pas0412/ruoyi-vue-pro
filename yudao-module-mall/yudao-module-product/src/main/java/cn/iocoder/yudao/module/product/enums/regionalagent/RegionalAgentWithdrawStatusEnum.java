package cn.iocoder.yudao.module.product.enums.regionalagent;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.Getter;

import java.util.Arrays;

/**
 * 地区代理提现状态枚举
 *
 * @author 芋道源码
 */
@Getter
public enum RegionalAgentWithdrawStatusEnum implements ArrayValuable<Integer> {

    AUDITING(0, "审核中"),
    AUDIT_SUCCESS(10, "审核通过"),
    AUDIT_FAIL(20, "审核不通过"),
    WITHDRAW_SUCCESS(11, "提现成功"),
    WITHDRAW_FAIL(21, "提现失败");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RegionalAgentWithdrawStatusEnum::getStatus).toArray(Integer[]::new);

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    RegionalAgentWithdrawStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

}