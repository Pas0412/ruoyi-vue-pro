package cn.iocoder.yudao.module.product.service.regionalagent;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.number.MoneyUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.module.pay.api.transfer.dto.PayTransferCreateReqDTO;
import cn.iocoder.yudao.module.pay.api.transfer.dto.PayTransferCreateRespDTO;
import cn.iocoder.yudao.module.pay.enums.PayChannelEnum;
import cn.iocoder.yudao.module.member.enums.MemberExperienceBizTypeEnum;
import cn.iocoder.yudao.module.pay.api.transfer.PayTransferApi;
import cn.iocoder.yudao.module.pay.api.wallet.PayWalletApi;
import cn.iocoder.yudao.module.pay.api.wallet.dto.PayWalletRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
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
import cn.iocoder.yudao.module.product.framework.config.ProductPayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

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
    
    @Resource
    private PayTransferApi payTransferApi;
    @Resource
    private PayWalletApi payWalletApi;
    
    @Resource
    private ProductPayProperties productPayProperties;

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
            regionalAgentWithdrawMapper.updateById(updateObj);
            
            // 执行转账逻辑
            auditRegionalAgentWithdrawSuccess(withdraw);
        } else {
            // 审核不通过，退还佣金
            updateObj.setStatus(RegionalAgentWithdrawStatusEnum.AUDIT_FAIL.getStatus());
            regionalAgentWithdrawMapper.updateById(updateObj);
            
            // 退还代理佣金
            RegionalAgentDO agent = regionalAgentService.getRegionalAgent(withdraw.getUserId());
            if (agent != null) {
                regionalAgentService.updateAgentPrice(agent.getId(), withdraw.getTotalPrice());
                
                // 记录佣金变动
                regionalAgentRecordService.addRegionalAgentBrokerage(agent.getId(), RegionalAgentRecordBizTypeEnum.WITHDRAW,
                        String.valueOf(withdraw.getId()), withdraw.getTotalPrice(), "提现审核不通过，退还佣金");
            }
        }
    }
    
    /**
     * 审核通过后的处理逻辑
     *
     * @param withdraw 提现记录
     */
    private void auditRegionalAgentWithdrawSuccess(RegionalAgentWithdrawDO withdraw) {
        // 情况一：通过 API 转账（微信、支付宝、钱包）
        if (isApiWithdrawType(withdraw.getType())) {
            createPayTransfer(withdraw);
            return;
        }

        // 情况二：非 API 转账（银行卡等手动打款）
        RegionalAgentWithdrawDO updateStatusObj = new RegionalAgentWithdrawDO();
        updateStatusObj.setStatus(RegionalAgentWithdrawStatusEnum.WITHDRAW_SUCCESS.getStatus());
        regionalAgentWithdrawMapper.updateByIdAndStatus(withdraw.getId(), 
            RegionalAgentWithdrawStatusEnum.AUDIT_SUCCESS.getStatus(),
            updateStatusObj);
    }
    
    /**
     * 判断是否为API转账类型
     *
     * @param type 提现类型
     * @return 是否为API转账类型
     */
    private boolean isApiWithdrawType(Integer type) {
        return Objects.equals(type, RegionalAgentWithdrawTypeEnum.WECHAT.getType()) ||
               Objects.equals(type, RegionalAgentWithdrawTypeEnum.ALIPAY.getType()) ||
               Objects.equals(type, RegionalAgentWithdrawTypeEnum.WALLET.getType());
    }
    
    /**
     * 创建支付转账
     *
     * @param withdraw 提现记录
     */
    private void createPayTransfer(RegionalAgentWithdrawDO withdraw) {
        // 1.1 获取基础信息
        String userAccount = withdraw.getAccountNo();
        String userName = withdraw.getName();
        String channelCode = null;
        Map<String, String> channelExtras = null;
        
        if (Objects.equals(withdraw.getType(), RegionalAgentWithdrawTypeEnum.ALIPAY.getType())) {
            channelCode = PayChannelEnum.ALIPAY_PC.getCode();
        } else if (Objects.equals(withdraw.getType(), RegionalAgentWithdrawTypeEnum.WECHAT.getType())) {
            channelCode = PayChannelEnum.WX_PUB.getCode();
            // 特殊：微信需要有报备信息
            channelExtras = PayTransferCreateReqDTO.buildWeiXinChannelExtra1000("地区代理佣金提现", "地区代理佣金提现");
        } else if (Objects.equals(withdraw.getType(), RegionalAgentWithdrawTypeEnum.WALLET.getType())) {
            PayWalletRespDTO wallet = payWalletApi.getOrCreateWallet(withdraw.getUserId(), UserTypeEnum.MEMBER.getValue());
            Assert.notNull(wallet, "钱包不存在");
            channelCode = PayChannelEnum.WALLET.getCode();
            userAccount = wallet.getId().toString();
        }
        
        // 1.2 构建请求
        PayTransferCreateReqDTO transferReqDTO = new PayTransferCreateReqDTO();
        transferReqDTO.setAppKey(productPayProperties.getPayAppKey());
        transferReqDTO.setChannelCode(channelCode);
        transferReqDTO.setMerchantTransferId(withdraw.getId().toString());
        transferReqDTO.setSubject("地区代理佣金提现");
        transferReqDTO.setPrice(withdraw.getPrice());
        transferReqDTO.setUserAccount(userAccount);
        transferReqDTO.setUserName(userName);
        transferReqDTO.setUserIp(getClientIP());
        transferReqDTO.setUserId(withdraw.getUserId());
        transferReqDTO.setUserType(UserTypeEnum.MEMBER.getValue()); // 用户信息
        transferReqDTO.setChannelExtras(channelExtras);
        
        // 1.3 发起请求
        PayTransferCreateRespDTO transferRespDTO = payTransferApi.createTransfer(transferReqDTO);

        // 2. 更新提现记录
        RegionalAgentWithdrawDO updateObj = new RegionalAgentWithdrawDO();
        updateObj.setId(withdraw.getId());
        updateObj.setPayTransferId(transferRespDTO.getId());
        updateObj.setTransferChannelCode(channelCode);
        regionalAgentWithdrawMapper.updateById(updateObj);
     }
     
     /**
      * 获取客户端IP地址
      *
      * @return 客户端IP地址
      */
     private String getClientIP() {
         return ServletUtils.getClientIP();
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