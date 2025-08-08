package cn.iocoder.yudao.module.trade.service.areaagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.system.api.area.SystemAreaApi;
import cn.iocoder.yudao.module.system.api.area.dto.SystemAreaRespDTO;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.record.TradeAreaAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentConfigDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentRecordDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentUserDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.iocoder.yudao.module.trade.dal.mysql.areaagent.TradeAreaAgentRecordMapper;
import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentRecordStatusEnum;
import cn.iocoder.yudao.module.trade.service.order.TradeOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 地区代理佣金记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TradeAreaAgentRecordServiceImpl implements TradeAreaAgentRecordService {

    @Resource
    private TradeAreaAgentRecordMapper areaAgentRecordMapper;

    @Resource
    private TradeAreaAgentUserService areaAgentUserService;

    @Resource
    private TradeAreaAgentConfigService areaAgentConfigService;

    @Resource
    private SystemAreaApi systemAreaApi;

    @Override
    public TradeAreaAgentRecordDO getAreaAgentRecord(Long id) {
        return areaAgentRecordMapper.selectById(id);
    }

    @Override
    public List<TradeAreaAgentRecordDO> getAreaAgentRecordList(List<Long> ids) {
        return areaAgentRecordMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TradeAreaAgentRecordDO> getAreaAgentRecordPage(TradeAreaAgentRecordPageReqVO pageReqVO) {
        return areaAgentRecordMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TradeAreaAgentRecordDO> getAreaAgentRecordListByUserId(Long userId) {
        return areaAgentRecordMapper.selectListByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAreaAgentCommission(TradeOrderDO order) {
        // 获取订单收货地区的所有上级地区
        List<SystemAreaRespDTO> parentAreas = systemAreaApi.getParentAreas(order.getReceiverAreaId());
        if (CollectionUtils.isEmpty(parentAreas)) {
            return;
        }

        // 获取所有启用的地区代理配置
        List<TradeAreaAgentConfigDO> configs = areaAgentConfigService.getEnabledAreaAgentConfigList();
        if (CollectionUtils.isEmpty(configs)) {
            return;
        }

        // 遍历上级地区，查找对应的代理用户
        for (SystemAreaRespDTO area : parentAreas) {
            TradeAreaAgentUserDO agentUser = areaAgentUserService.getAreaAgentUserByAreaId(area.getId());
            if (agentUser == null || !agentUser.getEnabled()) {
                continue;
            }

            // 获取对应等级的配置
            TradeAreaAgentConfigDO config = configs.stream()
                    .filter(c -> c.getAreaLevel().equals(area.getType()))
                    .findFirst()
                    .orElse(null);
            if (config == null || !config.getEnabled()) {
                continue;
            }

            // 计算佣金
            Integer commissionPrice = order.getPayPrice() * config.getCommissionPercent() / 100;
            if (commissionPrice <= 0) {
                continue;
            }

            // 创建佣金记录
            TradeAreaAgentRecordDO record = new TradeAreaAgentRecordDO();
            record.setUserId(agentUser.getUserId());
            record.setAreaId(agentUser.getAreaId());
            record.setAreaLevel(agentUser.getAreaLevel());
            record.setBizId(order.getNo());
            record.setBizType(AreaAgentRecordBizTypeEnum.ORDER.getType());
            record.setTitle("订单佣金");
            record.setPrice(commissionPrice);
            record.setTotalPrice(agentUser.getCommissionPrice() + agentUser.getFrozenPrice() + commissionPrice);
            record.setDescription(String.format("订单 %s 地区代理佣金", order.getNo()));
            record.setStatus(AreaAgentRecordStatusEnum.SETTLEMENT.getStatus());
            record.setFrozenDays(config.getFrozenDays());
            record.setUnfreezeTime(LocalDateTime.now().plusDays(config.getFrozenDays()));
            record.setSourceUserId(order.getUserId());
            record.setOrderAreaId(order.getReceiverAreaId());
            areaAgentRecordMapper.insert(record);

            // 更新代理用户佣金（冻结状态）
            areaAgentUserService.updateAreaAgentUserPrice(agentUser.getUserId(), 0, commissionPrice);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAreaAgentCommission(String bizId, Integer bizType) {
        // 查询对应的佣金记录
        List<TradeAreaAgentRecordDO> records = areaAgentRecordMapper.selectListByBizIdAndBizType(bizId, bizType);
        if (CollectionUtils.isEmpty(records)) {
            return;
        }

        // 取消佣金记录
        for (TradeAreaAgentRecordDO record : records) {
            // 更新记录状态为已取消
            TradeAreaAgentRecordDO updateObj = new TradeAreaAgentRecordDO();
            updateObj.setId(record.getId());
            updateObj.setStatus(AreaAgentRecordStatusEnum.CANCEL.getStatus());
            areaAgentRecordMapper.updateById(updateObj);

            // 扣减代理用户佣金
            if (AreaAgentRecordStatusEnum.SETTLEMENT.getStatus().equals(record.getStatus())) {
                // 如果是待结算状态，扣减冻结佣金
                areaAgentUserService.updateAreaAgentUserPrice(record.getUserId(), 0, -record.getPrice());
            } else if (AreaAgentRecordStatusEnum.SETTLED.getStatus().equals(record.getStatus())) {
                // 如果是已结算状态，扣减可用佣金
                areaAgentUserService.updateAreaAgentUserPrice(record.getUserId(), -record.getPrice(), 0);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int unfreezeAreaAgentCommission() {
        // 查询到期的冻结佣金记录
        List<TradeAreaAgentRecordDO> records = areaAgentRecordMapper.selectListByStatusAndUnfreezeTime(
                AreaAgentRecordStatusEnum.SETTLEMENT.getStatus(), LocalDateTime.now());
        if (CollectionUtils.isEmpty(records)) {
            return 0;
        }

        // 解冻佣金
        for (TradeAreaAgentRecordDO record : records) {
            // 更新记录状态为已结算
            TradeAreaAgentRecordDO updateObj = new TradeAreaAgentRecordDO();
            updateObj.setId(record.getId());
            updateObj.setStatus(AreaAgentRecordStatusEnum.SETTLED.getStatus());
            areaAgentRecordMapper.updateById(updateObj);

            // 将冻结佣金转为可用佣金
            areaAgentUserService.updateAreaAgentUserPrice(record.getUserId(), record.getPrice(), -record.getPrice());
        }
        
        return records.size();
    }

    @Override
    public Integer getSummaryPriceByUserId(Long userId, Integer status) {
        return areaAgentRecordMapper.selectSummaryPriceByUserIdAndStatus(userId, status);
    }

}