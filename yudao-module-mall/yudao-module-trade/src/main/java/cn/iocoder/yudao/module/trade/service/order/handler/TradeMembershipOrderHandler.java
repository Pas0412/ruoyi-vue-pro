package cn.iocoder.yudao.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.iocoder.yudao.module.trade.dal.mysql.order.TradeOrderMapper;
import cn.iocoder.yudao.module.trade.dal.mysql.order.TradeOrderItemMapper;
import cn.iocoder.yudao.module.trade.enums.order.TradeOrderStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.trade.enums.ErrorCodeConstants.*;

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
    @Resource
    private TradeOrderMapper tradeOrderMapper;
    @Resource
    private TradeOrderItemMapper tradeOrderItemMapper;

    @Override
    public void beforeOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 检查订单中是否包含会员商品
        List<TradeOrderItemDO> membershipItems = orderItems.stream()
                .filter(item -> isMembershipProduct(item.getSpuName()))
                .collect(Collectors.toList());
        
        if (CollUtil.isEmpty(membershipItems)) {
            return; // 没有会员商品，无需检查
        }
        
        // 1. 检查用户是否已经是有效会员
        if (memberUserApi.isValidMember(order.getUserId())) {
            throw exception(MEMBERSHIP_ALREADY_ACTIVE);
        }
//
//        // 2. 检查用户是否已经购买过会员商品
//        if (hasUserPurchasedMembershipProduct(order.getUserId())) {
//            throw exception(MEMBERSHIP_PRODUCT_ALREADY_PURCHASED);
//        }
        
        // 3. 检查本次订单中会员商品数量是否超过1件
        int totalMembershipCount = membershipItems.stream()
                .mapToInt(TradeOrderItemDO::getCount)
                .sum();
        
        if (totalMembershipCount > 1) {
            throw exception(MEMBERSHIP_PRODUCT_LIMIT_EXCEEDED);
        }
    }

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
     * 检查用户是否已经购买过会员商品
     */
    private boolean hasUserPurchasedMembershipProduct(Long userId) {
        // 查询用户所有已支付的订单
        List<TradeOrderDO> paidOrders = tradeOrderMapper.selectList(
                new LambdaQueryWrapper<TradeOrderDO>()
                        .eq(TradeOrderDO::getUserId, userId)
                        .eq(TradeOrderDO::getPayStatus, true)
                        .eq(TradeOrderDO::getDeleted, false)
        );
        
        if (CollUtil.isEmpty(paidOrders)) {
            return false;
        }
        
        // 获取所有订单项
        List<Long> orderIds = paidOrders.stream()
                .map(TradeOrderDO::getId)
                .collect(Collectors.toList());
        
        List<TradeOrderItemDO> orderItems = tradeOrderItemMapper.selectListByOrderId(orderIds);
        
        // 检查是否包含会员商品
        return orderItems.stream().anyMatch(item -> isMembershipProduct(item.getSpuName()));
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
        return now.plusYears(1); // 默认1年
    }
}