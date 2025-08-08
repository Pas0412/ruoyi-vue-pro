package cn.iocoder.yudao.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.trade.service.areaagent.TradeAreaAgentRecordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单地区代理的 {@link TradeOrderHandler} 实现类
 *
 * @author 芋道源码
 */
@Component
public class TradeAreaAgentOrderHandler implements TradeOrderHandler {

    @Resource
    private TradeAreaAgentRecordService areaAgentRecordService;

    @Override
    public void afterPayOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 订单支付成功后，添加地区代理佣金
        areaAgentRecordService.addAreaAgentCommission(order);
    }

    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 如果是未支付的订单，不会产生地区代理佣金，所以直接 return
        if (!order.getPayStatus()) {
            return;
        }

        // 售后的订单项，已经在 afterCancelOrderItem 回滚库存，所以这里不需要重复回滚
        orderItems = filterOrderItemListByNoneAfterSale(orderItems);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }
        
        // 取消地区代理佣金
        areaAgentRecordService.cancelAreaAgentCommission(order.getNo(), AreaAgentRecordBizTypeEnum.ORDER.getType());
    }

    @Override
    public void afterCancelOrderItem(TradeOrderDO order, TradeOrderItemDO orderItem) {
        // 订单项取消时，取消对应的地区代理佣金
        areaAgentRecordService.cancelAreaAgentCommission(String.valueOf(orderItem.getId()), AreaAgentRecordBizTypeEnum.ORDER.getType());
    }

}