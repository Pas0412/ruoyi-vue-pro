package cn.iocoder.yudao.module.product.service.regionalagent;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.number.MoneyUtils;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentDO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import cn.iocoder.yudao.module.product.dal.mysql.regionalagent.RegionalAgentRecordMapper;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentRecordStatusEnum;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentStatusEnum;
import cn.iocoder.yudao.module.product.service.regionalagent.bo.RegionalAgentAddReqBO;
import cn.iocoder.yudao.module.product.service.regionalagent.bo.UserRegionalAgentSummaryRespBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.product.enums.ErrorCodeConstants.REGIONAL_AGENT_WITHDRAW_USER_BALANCE_NOT_ENOUGH;

/**
 * 地区代理记录 Service 实现类
 *
 * @author 芋道源码
 */
@Slf4j
@Service
@Validated
public class RegionalAgentRecordServiceImpl implements RegionalAgentRecordService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RegionalAgentRecordServiceImpl.class);

    @Resource
    private RegionalAgentRecordMapper regionalAgentRecordMapper;
    @Resource
    private RegionalAgentService regionalAgentService;

    @Override
    public RegionalAgentRecordDO getRegionalAgentRecord(Long id) {
        return regionalAgentRecordMapper.selectById(id);
    }

    @Override
    public PageResult<RegionalAgentRecordDO> getRegionalAgentRecordPage(RegionalAgentRecordPageReqVO pageReqVO) {
        return regionalAgentRecordMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRegionalAgentBrokerage(Long userId, Integer areaId, RegionalAgentRecordBizTypeEnum bizType, List<RegionalAgentAddReqBO> list) {
        // 获取该地区的所有代理（按级别：县->市->省）
        List<RegionalAgentDO> agents = regionalAgentService.getRegionalAgentsByDeliveryArea(areaId);
        if (CollUtil.isEmpty(agents)) {
            log.info("[addRegionalAgentBrokerage][地区({})没有找到代理，跳过佣金分配]", areaId);
            return;
        }

        // 为每个代理分配佣金
        for (RegionalAgentDO agent : agents) {
            if (!RegionalAgentStatusEnum.APPROVED.getStatus().equals(agent.getStatus())) {
                continue;
            }
            
            // 计算该代理的佣金
            for (RegionalAgentAddReqBO addReqBO : list) {
                // 根据代理级别计算佣金比例（这里可以根据业务需求调整）
                BigDecimal brokeragePrice = calculateBrokerageByLevel(addReqBO.getPrice(), agent.getAreaType());
                if (brokeragePrice.compareTo(BigDecimal.ZERO) > 0) {
                    addRegionalAgentBrokerageRecord(agent, bizType, addReqBO.getBizId(), brokeragePrice, 
                            addReqBO.getTitle(), addReqBO.getDescription(), userId, getAgentLevel(agent.getAreaType()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRegionalAgentBrokerage(Long agentId, RegionalAgentRecordBizTypeEnum bizType, String bizId, BigDecimal brokeragePrice, String title) {
        RegionalAgentDO agent = regionalAgentService.getRegionalAgent(agentId);
        if (agent == null || !RegionalAgentStatusEnum.APPROVED.getStatus().equals(agent.getStatus())) {
            log.error("[addRegionalAgentBrokerage][代理({})不存在或状态不正确]", agentId);
            return;
        }

        addRegionalAgentBrokerageRecord(agent, bizType, bizId, brokeragePrice, title, null, null, null);
    }

    private void addRegionalAgentBrokerageRecord(RegionalAgentDO agent, RegionalAgentRecordBizTypeEnum bizType, 
                                                String bizId, BigDecimal brokeragePrice, String title, String description,
                                                Long sourceUserId, Integer sourceUserLevel) {
        // 创建佣金记录
        RegionalAgentRecordDO record = new RegionalAgentRecordDO();
        record.setUserId(agent.getUserId());
        record.setBizId(bizId);
        record.setBizType(bizType.getType());
        record.setTitle(title);
        record.setDescription(description);
        record.setPrice(brokeragePrice);
        record.setTotalPrice(agent.getBrokeragePrice().add(agent.getFrozenBrokeragePrice()).add(brokeragePrice));
        record.setStatus(RegionalAgentRecordStatusEnum.WAIT_SETTLEMENT.getStatus());
        record.setFrozenTime(LocalDateTime.now());
        record.setUnfreezeTime(LocalDateTime.now().plusDays(30)); // 30天后解冻
        record.setSourceUserId(sourceUserId);
        record.setSourceUserLevel(sourceUserLevel);
        regionalAgentRecordMapper.insert(record);

        // 更新代理的冻结佣金
        regionalAgentService.updateAgentFrozenPrice(agent.getId(), brokeragePrice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRegionalAgentBrokerage(RegionalAgentRecordBizTypeEnum bizType, String bizId) {
        List<RegionalAgentRecordDO> records = regionalAgentRecordMapper.selectListByBizTypeAndBizId(bizType.getType(), bizId);
        if (CollUtil.isEmpty(records)) {
            log.error("[cancelRegionalAgentBrokerage][bizId({}) bizType({}) 更新为已失效失败：记录不存在]", bizId, bizType);
            return;
        }

        records.forEach(record -> {
            // 1. 更新佣金记录为已失效
            RegionalAgentRecordDO updateObj = new RegionalAgentRecordDO();
            updateObj.setStatus(RegionalAgentRecordStatusEnum.CANCEL.getStatus());
            int updateRows = regionalAgentRecordMapper.updateByIdAndStatus(record.getId(), record.getStatus(), updateObj);
            if (updateRows == 0) {
                log.error("[cancelRegionalAgentBrokerage][record({}) 更新为已失效失败]", record.getId());
                return;
            }

            // 2. 更新代理的佣金
            RegionalAgentDO agent = regionalAgentService.getRegionalAgent(record.getUserId());
            if (agent != null) {
                if (RegionalAgentRecordStatusEnum.WAIT_SETTLEMENT.getStatus().equals(record.getStatus())) {
                    regionalAgentService.updateAgentFrozenPrice(agent.getId(), record.getPrice().negate());
                } else if (RegionalAgentRecordStatusEnum.SETTLEMENT.getStatus().equals(record.getStatus())) {
                    regionalAgentService.updateAgentPrice(agent.getId(), record.getPrice().negate());
                }
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int unfreezeRecord() {
        LocalDateTime unfreezeTime = LocalDateTime.now();
        // 分批处理，避免一次性处理太多数据
        int limit = 100;
        int totalCount = 0;
        
        while (true) {
            List<RegionalAgentRecordDO> records = regionalAgentRecordMapper.selectListByUnfreezeTimeAndStatus(unfreezeTime, limit);
            if (CollUtil.isEmpty(records)) {
                break;
            }
            
            for (RegionalAgentRecordDO record : records) {
                if (unfreezeRecord(record)) {
                    totalCount++;
                }
            }
            
            if (records.size() < limit) {
                break;
            }
        }
        
        return totalCount;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean unfreezeRecord(RegionalAgentRecordDO record) {
        // 更新记录状态为已结算
        RegionalAgentRecordDO updateObj = new RegionalAgentRecordDO();
        updateObj.setStatus(RegionalAgentRecordStatusEnum.SETTLEMENT.getStatus());
        int updateRows = regionalAgentRecordMapper.updateByIdAndStatus(record.getId(), 
                RegionalAgentRecordStatusEnum.WAIT_SETTLEMENT.getStatus(), updateObj);
        if (updateRows == 0) {
            return false;
        }

        // 更新代理佣金：冻结佣金减少，可用佣金增加
        RegionalAgentDO agent = regionalAgentService.getRegionalAgent(record.getUserId());
        if (agent != null) {
            regionalAgentService.updateFrozenPriceDecrAndPriceIncr(agent.getId(), record.getPrice(), record.getPrice());
        }
        
        return true;
    }

    @Override
    public List<UserRegionalAgentSummaryRespBO> getUserRegionalAgentSummaryListByUserId(Collection<Long> userIds, Integer bizType, Integer status) {
        if (CollUtil.isEmpty(userIds)) {
            return CollUtil.newArrayList();
        }
        return regionalAgentRecordMapper.selectSummaryListByUserIds(userIds, bizType, status);
    }

    @Override
    public Integer getSummaryPriceByUserId(Long userId, RegionalAgentRecordBizTypeEnum bizType, RegionalAgentRecordStatusEnum status,
                                           LocalDateTime beginTime, LocalDateTime endTime) {
        return regionalAgentRecordMapper.selectSummaryPriceByUserIdAndBizTypeAndStatusAndCreateTimeBetween(
                userId, bizType.getType(), status.getStatus(), beginTime, endTime);
    }

    /**
     * 根据基础价格和地区类型计算佣金
     *
     * @param basePrice 基础价格（分）
     * @param areaType  地区类型
     * @return 佣金金额（元，保留一位小数）
     */
    private BigDecimal calculateBrokerageByLevel(Integer basePrice, Integer areaType) {
        // 将基础金额从分转换为元
        BigDecimal basePriceYuan = new BigDecimal(basePrice).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        
        // 根据地区类型设置不同的佣金比例
        BigDecimal rate = BigDecimal.ZERO;
        if (areaType.equals(4)) { // 县级代理
            rate = new BigDecimal("0.20"); // 20%
        } else if (areaType.equals(3)) { // 市级代理
            rate = new BigDecimal("0.15"); // 15%
        } else if (areaType.equals(2)) { // 省级代理
            rate = new BigDecimal("0.10"); // 10%
        }
        
        // 计算佣金并保留一位小数
        return basePriceYuan.multiply(rate).setScale(1, RoundingMode.HALF_UP);
    }

    /**
     * 获取代理级别
     *
     * @param areaType 地区类型
     * @return 代理级别
     */
    private Integer getAgentLevel(Integer areaType) {
        if (areaType.equals(4)) { // 县级代理
            return 3;
        } else if (areaType.equals(3)) { // 市级代理
            return 2;
        } else if (areaType.equals(2)) { // 省级代理
            return 1;
        }
        return 0;
    }

    private RegionalAgentRecordServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}