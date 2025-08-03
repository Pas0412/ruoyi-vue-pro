package cn.iocoder.yudao.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderItemDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 会员订单的 {@link TradeOrderHandler} 实现类
 *
 * @author 系统
 */
@Component
@Slf4j
public class TradeMembershipOrderHandler implements TradeOrderHandler {

    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public void afterPayOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 检查是否包含会员商品
        boolean hasMembershipProduct = orderItems.stream()
                .anyMatch(item -> isMembershipProduct(item.getSpuName()));
        
        if (!hasMembershipProduct) {
            return;
        }
        
        try {
            // 根据商品名称计算到期时间
            LocalDateTime expireTime = calculateExpireTime(orderItems);
            
            // 激活用户会员
            memberUserApi.activateMember(order.getUserId(), expireTime);
            
            log.info("[afterPayOrder][order({}) 用户({}) 会员激活成功，到期时间:{}]", 
                    order.getId(), order.getUserId(), expireTime);
        } catch (Exception e) {
            log.error("[afterPayOrder][order({}) 会员激活失败]", order.getId(), e);
        }
    }
    
    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 检查是否包含会员商品
        boolean hasMembershipProduct = orderItems.stream()
                .anyMatch(item -> isMembershipProduct(item.getSpuName()));
        
        if (!hasMembershipProduct) {
            return;
        }
        
        try {
            // 取消用户会员（退款时）
            memberUserApi.cancelMember(order.getUserId());
            
            log.info("[afterCancelOrder][order({}) 用户({}) 会员取消成功]", 
                    order.getId(), order.getUserId());
        } catch (Exception e) {
            log.error("[afterCancelOrder][order({}) 会员取消失败]", order.getId(), e);
        }
    }
    
    /**
     * 判断是否为会员商品
     */
    private boolean isMembershipProduct(String spuName) {
        return spuName.contains("会员") || spuName.contains("VIP") || 
               spuName.contains("月卡") || spuName.contains("年卡");
    }
    
    /**
     * 根据商品名称计算到期时间
     */
    private LocalDateTime calculateExpireTime(List<TradeOrderItemDO> orderItems) {
        LocalDateTime now = LocalDateTime.now();
        
        for (TradeOrderItemDO item : orderItems) {
            String spuName = item.getSpuName();
            if (spuName.contains("年")) {
                return now.plusYears(1);
            } else if (spuName.contains("月")) {
                return now.plusMonths(1);
            } else if (spuName.contains("周")) {
                return now.plusWeeks(1);
            } else if (spuName.contains("天")) {
                return now.plusDays(1);
            }
        }
        return now.plusYears(1); // 默认1个月
    }
}