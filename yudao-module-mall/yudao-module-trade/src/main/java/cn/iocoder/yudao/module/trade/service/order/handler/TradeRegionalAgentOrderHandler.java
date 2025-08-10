package cn.iocoder.yudao.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import cn.iocoder.yudao.module.product.api.sku.ProductSkuApi;
import cn.iocoder.yudao.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.iocoder.yudao.module.product.api.spu.ProductSpuApi;
import cn.iocoder.yudao.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.iocoder.yudao.module.system.api.area.AreaApi;
import cn.iocoder.yudao.module.system.api.area.dto.AreaRespDTO;
import cn.iocoder.yudao.module.trade.dal.dataobject.config.TradeConfigDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent.RegionalAgentDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import cn.iocoder.yudao.module.trade.dal.mysql.regionalagent.RegionalAgentMapper;
import cn.iocoder.yudao.module.trade.dal.mysql.regionalagent.RegionalAgentRecordMapper;
import cn.iocoder.yudao.module.trade.service.regionalagent.RegionalAgentRecordService;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentRecordStatusEnum;
import cn.iocoder.yudao.module.trade.service.config.TradeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 地区代理的 {@link TradeOrderHandler} 实现类
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class TradeRegionalAgentOrderHandler implements TradeOrderHandler {

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private ProductSkuApi productSkuApi;
    @Resource
    private AreaApi areaApi;

    @Resource
    private TradeConfigService tradeConfigService;
    @Resource
    private RegionalAgentMapper regionalAgentMapper;
    @Resource
    private RegionalAgentRecordMapper regionalAgentRecordMapper;
    @Resource
    private RegionalAgentRecordService regionalAgentRecordService;

    @Override
    public void afterPayOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 1. 校验地区代理功能是否开启
        TradeConfigDO tradeConfig = tradeConfigService.getTradeConfig();
        if (tradeConfig == null || !Boolean.TRUE.equals(tradeConfig.getRegionalAgentEnabled())) {
            log.debug("[afterPayOrder][地区代理功能未开启，跳过佣金计算]");
            return;
        }

        // 2. 过滤排除的商品
        List<TradeOrderItemDO> validOrderItems = filterExcludedProducts(orderItems, tradeConfig);
        if (CollUtil.isEmpty(validOrderItems)) {
            log.debug("[afterPayOrder][订单中所有商品都被排除，跳过佣金计算]");
            return;
        }

        // 3. 获取用户收货地址信息
        MemberUserRespDTO user = memberUserApi.getUser(order.getUserId());
        if (user == null) {
            log.error("[afterPayOrder][用户({})不存在，跳过地区代理佣金计算]", order.getUserId());
            return;
        }

        // 4. 根据收货地址计算地区代理佣金
        calculateRegionalAgentCommission(order, validOrderItems, tradeConfig);
    }

    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 如果是未支付的订单，不会产生地区代理佣金，所以直接 return
        if (!order.getPayStatus()) {
            return;
        }

        // 售后的订单项，已经在 afterCancelOrderItem 回滚，所以这里不需要重复回滚
        orderItems = filterOrderItemListByNoneAfterSale(orderItems);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }

        // 取消地区代理佣金记录
        orderItems.forEach(orderItem -> afterCancelOrderItem(order, orderItem));
    }

    @Override
    public void afterCancelOrderItem(TradeOrderDO order, TradeOrderItemDO orderItem) {
        // 使用服务层取消对应的地区代理佣金记录
        regionalAgentRecordService.cancelRegionalAgentRecord(
            RegionalAgentRecordBizTypeEnum.ORDER, 
            String.valueOf(orderItem.getId())
        );
    }

    /**
     * 过滤排除的商品
     *
     * @param orderItems  订单项列表
     * @param tradeConfig 交易配置
     * @return 过滤后的订单项列表
     */
    private List<TradeOrderItemDO> filterExcludedProducts(List<TradeOrderItemDO> orderItems, TradeConfigDO tradeConfig) {
        // 如果没有配置排除商品，直接返回原列表
        if (StrUtil.isBlank(tradeConfig.getRegionalAgentExcludeSpuIds())) {
            return orderItems;
        }

        // 解析排除的商品SPU编号列表
        Set<Long> excludeSpuIds = Arrays.stream(tradeConfig.getRegionalAgentExcludeSpuIds().split(","))
                .map(String::trim)
                .filter(StrUtil::isNotBlank)
                .map(Long::valueOf)
                .collect(Collectors.toSet());

        if (CollUtil.isEmpty(excludeSpuIds)) {
            return orderItems;
        }

        // 过滤掉排除的商品
        return orderItems.stream()
                .filter(item -> !excludeSpuIds.contains(item.getSpuId()))
                .collect(Collectors.toList());
    }

    /**
     * 计算地区代理佣金
     *
     * @param order       订单
     * @param orderItems  订单项列表
     * @param tradeConfig 交易配置
     */
    private void calculateRegionalAgentCommission(TradeOrderDO order, List<TradeOrderItemDO> orderItems, TradeConfigDO tradeConfig) {
        // 获取收货地址信息
        Integer provinceId = order.getReceiverAreaId() != null ? getProvinceId(order.getReceiverAreaId()) : null;
        Integer cityId = order.getReceiverAreaId() != null ? getCityId(order.getReceiverAreaId()) : null;
        Integer districtId = order.getReceiverAreaId();

        if (provinceId == null) {
            log.warn("[calculateRegionalAgentCommission][订单({})收货地址信息不完整，跳过地区代理佣金计算]", order.getId());
            return;
        }

        // 计算各级代理佣金
        calculateProvinceAgentCommission(order, orderItems, tradeConfig, provinceId);
        if (cityId != null) {
            calculateCityAgentCommission(order, orderItems, tradeConfig, provinceId, cityId);
        }
        if (districtId != null) {
            calculateDistrictAgentCommission(order, orderItems, tradeConfig, provinceId, cityId, districtId);
        }
    }

    /**
     * 计算省级代理佣金
     */
    private void calculateProvinceAgentCommission(TradeOrderDO order, List<TradeOrderItemDO> orderItems, 
                                                  TradeConfigDO tradeConfig, Integer provinceId) {
        RegionalAgentDO agent = regionalAgentMapper.selectByAreaAndLevel(provinceId, null, null, 1);
        if (agent != null && agent.getStatus() == 1) { // 状态为已通过
            Integer commissionPercent = tradeConfig.getRegionalAgentProvincePercent();
            if (commissionPercent != null && commissionPercent > 0) {
                createCommissionRecords(order, orderItems, agent, commissionPercent, tradeConfig.getRegionalAgentFrozenDays(), 1);
            }
        }
    }

    /**
     * 计算市级代理佣金
     */
    private void calculateCityAgentCommission(TradeOrderDO order, List<TradeOrderItemDO> orderItems, 
                                              TradeConfigDO tradeConfig, Integer provinceId, Integer cityId) {
        RegionalAgentDO agent = regionalAgentMapper.selectByAreaAndLevel(provinceId, cityId, null, 2);
        if (agent != null && agent.getStatus() == 1) { // 状态为已通过
            Integer commissionPercent = tradeConfig.getRegionalAgentCityPercent();
            if (commissionPercent != null && commissionPercent > 0) {
                createCommissionRecords(order, orderItems, agent, commissionPercent, tradeConfig.getRegionalAgentFrozenDays(), 2);
            }
        }
    }

    /**
     * 计算县级代理佣金
     */
    private void calculateDistrictAgentCommission(TradeOrderDO order, List<TradeOrderItemDO> orderItems, 
                                                  TradeConfigDO tradeConfig, Integer provinceId, Integer cityId, Integer districtId) {
        RegionalAgentDO agent = regionalAgentMapper.selectByAreaAndLevel(provinceId, cityId, districtId, 3);
        if (agent != null && agent.getStatus() == 1) { // 状态为已通过
            Integer commissionPercent = tradeConfig.getRegionalAgentDistrictPercent();
            if (commissionPercent != null && commissionPercent > 0) {
                createCommissionRecords(order, orderItems, agent, commissionPercent, tradeConfig.getRegionalAgentFrozenDays(), 3);
            }
        }
    }

    /**
     * 创建佣金记录
     */
    private void createCommissionRecords(TradeOrderDO order, List<TradeOrderItemDO> orderItems, 
                                         RegionalAgentDO agent, Integer commissionPercent, 
                                         Integer frozenDays, Integer agentLevel) {
        // 获取商品信息
        Map<Long, ProductSpuRespDTO> spuMap = productSpuApi.getSpuMap(convertSet(orderItems, TradeOrderItemDO::getSpuId));
        Map<Long, ProductSkuRespDTO> skuMap = productSkuApi.getSkuMap(convertSet(orderItems, TradeOrderItemDO::getSkuId));

        for (TradeOrderItemDO orderItem : orderItems) {
            ProductSpuRespDTO spu = spuMap.get(orderItem.getSpuId());
            ProductSkuRespDTO sku = skuMap.get(orderItem.getSkuId());
            
            if (spu == null || sku == null) {
                continue;
            }

            // 计算佣金金额（基于订单项的实际支付金额）
            int commissionAmount = calculateCommissionAmount(orderItem.getPayPrice(), commissionPercent);
            if (commissionAmount <= 0) {
                continue;
            }

            // 使用服务层创建佣金记录
            String title = String.format("订单佣金-%s", spu.getName());
            String description = String.format("订单号：%s，商品：%s", order.getNo(), spu.getName());
            
            regionalAgentRecordService.createRegionalAgentRecord(
                agent.getId(),
                agent.getUserId(), 
                RegionalAgentRecordBizTypeEnum.ORDER,
                String.valueOf(orderItem.getId()),
                commissionAmount,
                title,
                description,
                frozenDays,
                agentLevel,
                order.getUserId()
            );
        }

        log.info("[createCommissionRecords][为代理({})创建{}级佣金记录]", agent.getId(), agentLevel);
    }

    /**
     * 计算佣金金额
     *
     * @param payPrice         支付金额（分）
     * @param commissionPercent 佣金比例（百分比）
     * @return 佣金金额（分）
     */
    private int calculateCommissionAmount(Integer payPrice, Integer commissionPercent) {
        if (payPrice == null || payPrice <= 0 || commissionPercent == null || commissionPercent <= 0) {
            return 0;
        }
        return (int) Math.floor(payPrice * commissionPercent / 100.0);
    }

    /**
     * 根据区县ID获取省份ID
     */
    private Integer getProvinceId(Integer areaId) {
        try {
            AreaRespDTO area = areaApi.getArea(areaId);
            if (area == null) {
                return null;
            }
            
            // 如果是省级，直接返回
            if (area.getType() == 1) {
                return area.getId();
            }
            
            // 如果是市级，返回父级ID
            if (area.getType() == 2) {
                return area.getParentId();
            }
            
            // 如果是区县级，需要向上查找省级
            if (area.getType() == 3) {
                AreaRespDTO city = areaApi.getArea(area.getParentId());
                if (city != null) {
                    return city.getParentId();
                }
            }
            
            return null;
        } catch (Exception e) {
            log.error("[getProvinceId][获取省份ID失败，areaId:{}]", areaId, e);
            return null;
        }
    }

    /**
     * 根据区县ID获取城市ID
     */
    private Integer getCityId(Integer areaId) {
        try {
            AreaRespDTO area = areaApi.getArea(areaId);
            if (area == null) {
                return null;
            }
            
            // 如果是市级，直接返回
            if (area.getType() == 2) {
                return area.getId();
            }
            
            // 如果是区县级，返回父级ID
            if (area.getType() == 3) {
                return area.getParentId();
            }
            
            return null;
        } catch (Exception e) {
            log.error("[getCityId][获取城市ID失败，areaId:{}]", areaId, e);
            return null;
        }
    }
}