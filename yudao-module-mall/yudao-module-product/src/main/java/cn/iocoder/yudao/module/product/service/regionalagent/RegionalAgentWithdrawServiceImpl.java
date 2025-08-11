package cn.iocoder.yudao.module.product.service.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.number.MoneyUtils;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentWithdrawApproveReqVO;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentWithdrawCreateReqVO;
import cn.iocoder.yudao.module.product.controller.app.regionalagent.vo.AppRegionalAgentWithdrawCreateReqVO;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentWithdrawPageReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentDO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentWithdrawDO;
import cn.iocoder.yudao.module.product.dal.mysql.regionalagent.RegionalAgentWithdrawMapper;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentStatusEnum;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentWithdrawStatusEnum;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentWithdrawTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.product.enums.ErrorCodeConstants.*;

/**
 * 地区代理提现 Service 实现类
 *
 * @author 芋道源码
 */
@Slf4j
@Service
@Validated
public class RegionalAgentWithdrawServiceImpl implements RegionalAgentWithdrawService {

    @Resource
    private RegionalAgentWithdrawMapper regionalAgentWithdrawMapper;
    @Resource
    private RegionalAgentService regionalAgentService;
    @Resource
    private RegionalAgentRecordService regionalAgentRecordService;

    @Override
    public RegionalAgentWithdrawDO getRegionalAgentWithdraw(Long id) {
        return regionalAgentWithdrawMapper.selectById(id);
    }

    @Override
    public PageResult<RegionalAgentWithdrawDO> getRegionalAgentWithdrawPage(RegionalAgentWithdrawPageReqVO pageReqVO) {
        return regionalAgentWithdrawMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRegionalAgentWithdraw(Long userId, AppRegionalAgentWithdrawCreateReqVO createReqVO) {
        // 1. 校验用户是否为代理
        RegionalAgentDO agent = regionalAgentService.getRegionalAgentByUserId(userId, RegionalAgentStatusEnum.APPROVED);
        if (agent == null) {
            throw exception(REGIONAL_AGENT_NOT_EXISTS);
        }

        // 2. 校验提现金额
        if (createReqVO.getPrice() <= 0) {
            throw exception(REGIONAL_AGENT_WITHDRAW_PRICE_ERROR);
        }
        if (createReqVO.getPrice() > agent.getBrokeragePrice()) {
            throw exception(REGIONAL_AGENT_WITHDRAW_USER_BALANCE_NOT_ENOUGH);
        }

        // 3. 计算手续费
        Integer feePrice = calculateFeePrice(createReqVO.getPrice(), createReqVO.getType());
        Integer totalPrice = createReqVO.getPrice() + feePrice;

        // 4. 创建提现记录
        RegionalAgentWithdrawDO withdraw = new RegionalAgentWithdrawDO();
        withdraw.setUserId(userId);
        withdraw.setPrice(createReqVO.getPrice());
        withdraw.setFeePrice(feePrice);
        withdraw.setTotalPrice(totalPrice);
        withdraw.setType(createReqVO.getType());
        withdraw.setName(createReqVO.getName());
        withdraw.setAccountNo(createReqVO.getAccountNo());
        withdraw.setAccountQrCodeUrl(createReqVO.getAccountQrCodeUrl());
        withdraw.setBankName(createReqVO.getBankName());
        withdraw.setBankAddress(createReqVO.getBankAddress());
        withdraw.setStatus(RegionalAgentWithdrawStatusEnum.AUDITING.getStatus());
        withdraw.setRemark(createReqVO.getRemark());
        regionalAgentWithdrawMapper.insert(withdraw);

        // 5. 扣减代理可用佣金
        regionalAgentService.updateAgentPrice(agent.getId(), -totalPrice);

        // 6. 记录佣金变动
        regionalAgentRecordService.addRegionalAgentBrokerage(agent.getId(), RegionalAgentRecordBizTypeEnum.WITHDRAW,
                String.valueOf(withdraw.getId()), -totalPrice, "提现申请");

        return withdraw.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditRegionalAgentWithdraw(Long id, Integer status, String auditReason) {
        // 1. 校验提现记录
        RegionalAgentWithdrawDO withdraw = validateWithdrawForApprove(id);

        // 2. 更新提现记录
        RegionalAgentWithdrawDO updateObj = new RegionalAgentWithdrawDO();
        updateObj.setId(withdraw.getId());
        updateObj.setAuditTime(LocalDateTime.now());
        updateObj.setAuditReason(auditReason);

        if (status.equals(RegionalAgentWithdrawStatusEnum.AUDIT_SUCCESS.getStatus())) {
            // 审核通过
            updateObj.setStatus(RegionalAgentWithdrawStatusEnum.AUDIT_SUCCESS.getStatus());
        } else {
            // 审核不通过，退还佣金
            updateObj.setStatus(RegionalAgentWithdrawStatusEnum.AUDIT_FAIL.getStatus());
            
            // 退还代理佣金
            RegionalAgentDO agent = regionalAgentService.getRegionalAgent(withdraw.getUserId());
            if (agent != null) {
                regionalAgentService.updateAgentPrice(agent.getId(), withdraw.getTotalPrice());
                
                // 记录佣金变动
                regionalAgentRecordService.addRegionalAgentBrokerage(agent.getId(), RegionalAgentRecordBizTypeEnum.WITHDRAW,
                        String.valueOf(withdraw.getId()), withdraw.getTotalPrice(), "提现审核不通过，退还佣金");
            }
        }

        regionalAgentWithdrawMapper.updateById(updateObj);
    }

    /**
     * 校验提现记录，用于审核
     *
     * @param id 提现记录编号
     * @return 提现记录
     */
    private RegionalAgentWithdrawDO validateWithdrawForApprove(Long id) {
        RegionalAgentWithdrawDO withdraw = regionalAgentWithdrawMapper.selectById(id);
        if (withdraw == null) {
            throw exception(REGIONAL_AGENT_WITHDRAW_NOT_EXISTS);
        }
        if (!RegionalAgentWithdrawStatusEnum.AUDITING.getStatus().equals(withdraw.getStatus())) {
            throw exception(REGIONAL_AGENT_WITHDRAW_STATUS_NOT_AUDITING);
        }
        return withdraw;
    }

    /**
     * 计算提现手续费
     *
     * @param price 提现金额
     * @param type  提现类型
     * @return 手续费
     */
    private Integer calculateFeePrice(Integer price, Integer type) {
        // 根据提现类型计算手续费
        double feeRate = 0.0;
        if (RegionalAgentWithdrawTypeEnum.WALLET.getType().equals(type)) {
            feeRate = 0.0; // 钱包提现免手续费
        } else if (RegionalAgentWithdrawTypeEnum.BANK.getType().equals(type)) {
            feeRate = 0.005; // 银行卡提现 0.5% 手续费
        } else if (RegionalAgentWithdrawTypeEnum.WECHAT.getType().equals(type)) {
            feeRate = 0.003; // 微信提现 0.3% 手续费
        } else if (RegionalAgentWithdrawTypeEnum.ALIPAY.getType().equals(type)) {
            feeRate = 0.003; // 支付宝提现 0.3% 手续费
        }
        
        return MoneyUtils.calculateRatePriceFloor(price, feeRate);
    }

}