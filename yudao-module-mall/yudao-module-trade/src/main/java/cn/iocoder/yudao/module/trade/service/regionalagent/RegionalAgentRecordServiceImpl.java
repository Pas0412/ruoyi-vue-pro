package cn.iocoder.yudao.module.trade.service.regionalagent;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.number.MoneyUtils;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.record.RegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.record.AppRegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.config.TradeConfigDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent.RegionalAgentDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import cn.iocoder.yudao.module.trade.dal.mysql.regionalagent.RegionalAgentMapper;
import cn.iocoder.yudao.module.trade.dal.mysql.regionalagent.RegionalAgentRecordMapper;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentRecordStatusEnum;
import cn.iocoder.yudao.module.trade.service.config.TradeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.trade.enums.ErrorCodeConstants.BROKERAGE_WITHDRAW_USER_BALANCE_NOT_ENOUGH;

/**
 * 地区代理佣金记录 Service 实现类
 *
 * @author 芋道源码
 */
@Slf4j
@Service
@Validated
public class RegionalAgentRecordServiceImpl implements RegionalAgentRecordService {

    @Resource
    private RegionalAgentRecordMapper regionalAgentRecordMapper;
    @Resource
    private RegionalAgentMapper regionalAgentMapper;
    @Resource
    private TradeConfigService tradeConfigService;

    @Override
    public RegionalAgentRecordDO getRegionalAgentRecord(Long id) {
        return regionalAgentRecordMapper.selectById(id);
    }

    @Override
    public PageResult<RegionalAgentRecordDO> getRegionalAgentRecordPage(RegionalAgentRecordPageReqVO pageReqVO) {
        return regionalAgentRecordMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<RegionalAgentRecordDO> getRegionalAgentRecordPage(AppRegionalAgentRecordPageReqVO pageReqVO, Long userId) {
        return regionalAgentRecordMapper.selectPage(pageReqVO, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRegionalAgentRecord(Long userId, RegionalAgentRecordBizTypeEnum bizType, String bizId, Integer agentPrice, String title) {
        // 1. 校验地区代理功能是否启用
        TradeConfigDO tradeConfig = tradeConfigService.getTradeConfig();
        if (tradeConfig == null || !BooleanUtil.isTrue(tradeConfig.getRegionalAgentEnabled())) {
            log.error("[addRegionalAgentRecord][增加地区代理佣金失败：regionalAgentEnabled 未配置，userId({}) bizType({}) agentPrice({})]", userId, bizType, agentPrice);
            return;
        }

        // 2. 获取用户的地区代理信息
        List<RegionalAgentDO> agents = regionalAgentMapper.selectListByUserId(userId);
        if (CollUtil.isEmpty(agents)) {
            log.error("[addRegionalAgentRecord][用户({}) 不是地区代理]", userId);
            return;
        }

        // 3. 校验佣金余额（如果是扣减佣金）
        if (agentPrice < 0) {
            RegionalAgentDO agent = agents.get(0); // 取第一个代理信息
            int balance = Optional.ofNullable(agent.getAgentPrice()).orElse(0);
            if (balance + agentPrice < 0) {
                throw exception(BROKERAGE_WITHDRAW_USER_BALANCE_NOT_ENOUGH, MoneyUtils.fenToYuanStr(balance));
            }
        }

        // 4. 更新代理佣金余额
        RegionalAgentDO agent = agents.get(0); // 取第一个代理信息
        boolean success;
        if (agentPrice > 0) {
            regionalAgentMapper.updateAgentPriceIncr(agent.getId(), agentPrice);
            success = true;
        } else {
            success = regionalAgentMapper.updateAgentPriceDecr(agent.getId(), -agentPrice) > 0;
        }
        
        if (!success) {
            // 失败时，则抛出异常。只会出现扣减佣金时，余额不足的情况
            int balance = Optional.ofNullable(agent.getAgentPrice()).orElse(0);
            throw exception(BROKERAGE_WITHDRAW_USER_BALANCE_NOT_ENOUGH, MoneyUtils.fenToYuanStr(balance));
        }

        // 5. 新增佣金记录
        RegionalAgentRecordDO record = new RegionalAgentRecordDO()
                .setAgentId(agent.getId())
                .setUserId(userId)
                .setBizType(bizType.getType())
                .setBizId(bizId)
                .setPrice(agentPrice)
                .setTotalPrice(agent.getAgentPrice() + agentPrice)
                .setTitle(title)
                .setDescription(String.format(bizType.getDescription(), MoneyUtils.fenToYuanStr(Math.abs(agentPrice))))
                .setStatus(RegionalAgentRecordStatusEnum.SETTLEMENT.getStatus()) // 直接结算，不冻结
                .setFrozenDays(0)
                .setUnfreezeTime(null)
                .setAgentLevel(agent.getAgentLevel())
                .setSourceUserId(null);
        regionalAgentRecordMapper.insert(record);
    }

    /**
     * 创建地区代理佣金记录（支持冻结功能）
     *
     * @param agentId        代理编号
     * @param userId         用户编号
     * @param bizType        业务类型
     * @param bizId          业务编号
     * @param agentPrice     佣金金额
     * @param title          标题
     * @param description    描述
     * @param frozenDays     冻结天数
     * @param agentLevel     代理级别
     * @param sourceUserId   来源用户编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void createRegionalAgentRecord(Long agentId, Long userId, RegionalAgentRecordBizTypeEnum bizType, String bizId, 
                                         Integer agentPrice, String title, String description, Integer frozenDays, 
                                         Integer agentLevel, Long sourceUserId) {
        // 1. 处理冻结时间
        LocalDateTime unfreezeTime = null;
        Integer status;
        if (frozenDays != null && frozenDays > 0) {
            unfreezeTime = LocalDateTime.now().plusDays(frozenDays);
            status = RegionalAgentRecordStatusEnum.WAIT_SETTLEMENT.getStatus();
        } else {
            status = RegionalAgentRecordStatusEnum.SETTLEMENT.getStatus();
        }

        // 2. 获取代理信息计算总佣金
        RegionalAgentDO agent = regionalAgentMapper.selectById(agentId);
        if (agent == null) {
            log.error("[createRegionalAgentRecord][代理({})不存在]", agentId);
            return;
        }

        // 3. 新增佣金记录
        RegionalAgentRecordDO record = new RegionalAgentRecordDO()
                .setAgentId(agentId)
                .setUserId(userId)
                .setBizType(bizType.getType())
                .setBizId(bizId)
                .setPrice(agentPrice)
                .setTotalPrice(agent.getAgentPrice() + agent.getFrozenPrice() + agentPrice)
                .setTitle(title)
                .setDescription(description)
                .setStatus(status)
                .setFrozenDays(frozenDays)
                .setUnfreezeTime(unfreezeTime)
                .setAgentLevel(agentLevel)
                .setSourceUserId(sourceUserId);
        regionalAgentRecordMapper.insert(record);

        // 4. 更新代理佣金
        if (frozenDays != null && frozenDays > 0) {
            // 增加冻结佣金
            regionalAgentMapper.updateFrozenPriceIncr(agentId, agentPrice);
        } else {
            // 增加可用佣金
            regionalAgentMapper.updateAgentPriceIncr(agentId, agentPrice);
        }

        log.info("[createRegionalAgentRecord][为代理({})创建佣金记录，金额：{}分，冻结天数：{}]", agentId, agentPrice, frozenDays);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRegionalAgentRecord(RegionalAgentRecordBizTypeEnum bizType, String bizId) {
        List<RegionalAgentRecordDO> records = regionalAgentRecordMapper.selectListByBizTypeAndBizId(bizType.getType(), bizId);
        if (CollUtil.isEmpty(records)) {
            log.error("[cancelRegionalAgentRecord][bizId({}) bizType({}) 更新为已失效失败：记录不存在]", bizId, bizType);
            return;
        }

        records.forEach(record -> {
            // 1. 更新佣金记录为已失效
            RegionalAgentRecordDO updateObj = new RegionalAgentRecordDO().setStatus(RegionalAgentRecordStatusEnum.CANCEL.getStatus());
            int updateRows = regionalAgentRecordMapper.updateByIdAndStatus(record.getId(), record.getStatus(), updateObj);
            if (updateRows == 0) {
                log.error("[cancelRegionalAgentRecord][record({}) 更新为已失效失败]", record.getId());
                return;
            }

            // 2. 更新代理的佣金
            if (RegionalAgentRecordStatusEnum.WAIT_SETTLEMENT.getStatus().equals(record.getStatus())) {
                // 待结算状态，扣减冻结佣金
                regionalAgentMapper.updateFrozenPriceDecr(record.getAgentId(), record.getPrice());
            } else if (RegionalAgentRecordStatusEnum.SETTLEMENT.getStatus().equals(record.getStatus())) {
                // 已结算状态，扣减可用佣金
                regionalAgentMapper.updateAgentPriceDecr(record.getAgentId(), record.getPrice());
            }
        });
    }

    @Override
    public int unfreezeRecord() {
        // 1. 查询待结算的佣金记录
        List<RegionalAgentRecordDO> records = regionalAgentRecordMapper.selectListByUnfreezeTimeLe(LocalDateTime.now());
        if (CollUtil.isEmpty(records)) {
            return 0;
        }

        // 2. 遍历执行
        int count = 0;
        for (RegionalAgentRecordDO record : records) {
            try {
                boolean success = getSelf().unfreezeRecord(record);
                if (success) {
                    count++;
                }
            } catch (Exception e) {
                log.error("[unfreezeRecord][record({}) 更新为已结算失败]", record.getId(), e);
            }
        }
        return count;
    }

    /**
     * 解冻单条地区代理佣金记录
     *
     * @param record 佣金记录
     * @return 解冻是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean unfreezeRecord(RegionalAgentRecordDO record) {
        // 更新记录状态
        RegionalAgentRecordDO updateObj = new RegionalAgentRecordDO()
                .setStatus(RegionalAgentRecordStatusEnum.SETTLEMENT.getStatus())
                .setUnfreezeTime(LocalDateTime.now());
        int updateRows = regionalAgentRecordMapper.updateByIdAndStatus(record.getId(), record.getStatus(), updateObj);
        if (updateRows == 0) {
            log.error("[unfreezeRecord][record({}) 更新为已结算失败]", record.getId());
            return false;
        }

        // 更新代理冻结佣金转为可用佣金
        int updateAgentRows = regionalAgentMapper.updateFrozenPriceDecrAndAgentPriceIncr(record.getAgentId(), record.getPrice());
        if (updateAgentRows == 0) {
            log.error("[unfreezeRecord][agentId({}) 冻结佣金转可用佣金失败]", record.getAgentId());
            return false;
        }
        
        log.info("[unfreezeRecord][record({}) 更新为已结算成功]", record.getId());
        return true;
    }

    @Override
    public Integer getYesterdayPrice(Long userId, Integer bizType, Integer status, LocalDateTime beginTime, LocalDateTime endTime) {
        return regionalAgentRecordMapper.selectPriceSummaryByUserIdAndBizTypeAndCreateTimeBetween(userId, bizType, status, beginTime, endTime);
    }

    @Override
    public Integer getTotalPrice(Long userId, Integer bizType, Integer status) {
        return regionalAgentRecordMapper.selectPriceSummaryByUserIdAndBizTypeAndStatus(userId, bizType, status);
    }

    /**
     * 获得自身的代理对象，解决 AOP 生效问题
     *
     * @return 自己
     */
    private RegionalAgentRecordServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}