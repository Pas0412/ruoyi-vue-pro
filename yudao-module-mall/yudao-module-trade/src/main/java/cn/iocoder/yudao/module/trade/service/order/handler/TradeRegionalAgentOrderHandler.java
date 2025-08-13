package cn.iocoder.yudao.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import cn.iocoder.yudao.module.product.api.sku.ProductSkuApi;
import cn.iocoder.yudao.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.iocoder.yudao.module.product.api.spu.ProductSpuApi;
import cn.iocoder.yudao.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.iocoder.yudao.module.product.service.regionalagent.RegionalAgentRecordService;
import cn.iocoder.yudao.module.product.service.regionalagent.bo.RegionalAgentAddReqBO;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderItemDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

/**
 * 地区代理订单的 {@link TradeOrderHandler} 实现类
 *
 * @author 芋道源码
 */
@Component
public class TradeRegionalAgentOrderHandler implements TradeOrderHandler {

    /**
     * 会员商品ID集合（不参与地区代理分佣的商品）
     */
    private static final Set<Long> MEMBERSHIP_PRODUCT_IDS = new HashSet<>(Arrays.asList(
        643L  // 会员商品ID
        // 可以在这里添加更多会员商品ID
    ));

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private ProductSkuApi productSkuApi;

    @Resource
    private RegionalAgentRecordService regionalAgentRecordService;

    @Override
    public void afterPayOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 过滤掉会员商品，会员商品不参与地区代理分佣
        List<TradeOrderItemDO> filteredOrderItems = filterNonMembershipProducts(orderItems);
        if (CollUtil.isEmpty(filteredOrderItems)) {
            return;
        }
        
        // 获取收货地址的地区ID，用于分配地区代理佣金
        Integer areaId = order.getReceiverAreaId();
        if (areaId == null) {
            return;
        }
        
        addRegionalAgentBrokerage(order.getUserId(), areaId, filteredOrderItems);
    }

    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 如果是未支付的订单，不会产生地区代理佣金，所以直接 return
        if (!order.getPayStatus()) {
            return;
        }
        
        // 售后的订单项，已经在 afterCancelOrderItem 回滚佣金，所以这里不需要重复回滚
        orderItems = filterOrderItemListByNoneAfterSale(orderItems);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }
        
        // 取消地区代理佣金
        orderItems.forEach(orderItem -> {
            regionalAgentRecordService.cancelRegionalAgentBrokerage(
                RegionalAgentRecordBizTypeEnum.ORDER, 
                String.valueOf(orderItem.getId())
            );
        });
    }

    @Override
    public void afterCancelOrderItem(TradeOrderDO order, TradeOrderItemDO orderItem) {
        // 取消单个订单项的地区代理佣金
        regionalAgentRecordService.cancelRegionalAgentBrokerage(
            RegionalAgentRecordBizTypeEnum.ORDER, 
            String.valueOf(orderItem.getId())
        );
    }

    /**
     * 过滤掉会员商品，会员商品不参与地区代理分佣
     *
     * @param orderItems 订单项列表
     * @return 过滤后的订单项列表
     */
    private List<TradeOrderItemDO> filterNonMembershipProducts(List<TradeOrderItemDO> orderItems) {
        if (CollUtil.isEmpty(orderItems)) {
            return orderItems;
        }
        
        // 过滤掉会员商品（通过商品ID判断）
        return convertList(orderItems, orderItem -> {
            // 判断是否为会员商品（通过商品ID判断）
            if (isMembershipProduct(orderItem.getSpuId())) {
                return null;
            }
            
            return orderItem;
        });
    }

    /**
     * 判断是否为会员商品
     *
     * @param spuId 商品SPU ID
     * @return 是否为会员商品
     */
    private boolean isMembershipProduct(Long spuId) {
        if (spuId == null) {
            return false;
        }
        
        return MEMBERSHIP_PRODUCT_IDS.contains(spuId);
    }

    /**
     * 创建地区代理佣金记录
     *
     * @param userId     用户编号
     * @param areaId     地区编号
     * @param orderItems 订单项
     */
    protected void addRegionalAgentBrokerage(Long userId, Integer areaId, List<TradeOrderItemDO> orderItems) {
        MemberUserRespDTO user = memberUserApi.getUser(userId);
        Assert.notNull(user);
        
        Map<Long, ProductSpuRespDTO> spusMap = productSpuApi.getSpuMap(
            convertList(orderItems, TradeOrderItemDO::getSpuId)
        );
        Map<Long, ProductSkuRespDTO> skusMap = productSkuApi.getSkuMap(
            convertList(orderItems, TradeOrderItemDO::getSkuId)
        );

        // 每一个订单项，都会去生成地区代理佣金记录
        List<RegionalAgentAddReqBO> addList = convertList(orderItems, item -> {
            ProductSpuRespDTO spu = spusMap.get(item.getSpuId());
            Assert.notNull(spu);
            ProductSkuRespDTO sku = skusMap.get(item.getSkuId());
            Assert.notNull(sku);
            
            RegionalAgentAddReqBO addReqBO = new RegionalAgentAddReqBO();
            addReqBO.setBizId(String.valueOf(item.getId()));
            addReqBO.setPrice(item.getPayPrice());
            addReqBO.setTitle("订单佣金：" + spu.getName());
            addReqBO.setDescription("订单号：" + item.getOrderId() + "，商品：" + spu.getName());
            return addReqBO;
        });
        
        regionalAgentRecordService.addRegionalAgentBrokerage(
            userId, areaId, RegionalAgentRecordBizTypeEnum.ORDER, addList
        );
    }

}