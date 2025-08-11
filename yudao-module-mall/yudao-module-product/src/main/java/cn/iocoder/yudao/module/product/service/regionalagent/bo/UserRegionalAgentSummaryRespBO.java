package cn.iocoder.yudao.module.product.service.regionalagent.bo;

import lombok.Data;

/**
 * 用户地区代理汇总 Response BO
 *
 * @author 芋道源码
 */
@Data
public class UserRegionalAgentSummaryRespBO {

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 佣金，单位：分
     */
    private Integer price;
    /**
     * 佣金笔数
     */
    private Integer count;

}