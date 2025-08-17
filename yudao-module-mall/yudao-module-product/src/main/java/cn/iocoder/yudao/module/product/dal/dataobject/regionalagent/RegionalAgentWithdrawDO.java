package cn.iocoder.yudao.module.product.dal.dataobject.regionalagent;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 区域代理提现 DO
 *
 * @author 芋道源码
 */
@TableName("product_regional_agent_withdraw")
@KeySequence("product_regional_agent_withdraw_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionalAgentWithdrawDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     *
     * 关联 MemberUserDO 的 id 字段
     */
    private Long userId;
    /**
     * 提现金额，单位：元
     */
    private BigDecimal price;
    /**
     * 手续费，单位：元
     */
    private BigDecimal feePrice;
    /**
     * 当前总佣金，单位：元
     */
    private BigDecimal totalPrice;
    /**
     * 提现类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentWithdrawTypeEnum}
     */
    private Integer type;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 账号
     */
    private String accountNo;
    /**
     * 收款码
     */
    private String accountQrCodeUrl;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 开户地址
     */
    private String bankAddress;
    /**
     * 状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentWithdrawStatusEnum}
     */
    private Integer status;
    /**
     * 审核驳回原因
     */
    private String auditReason;
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
    /**
     * 审核用户编号
     */
    private Long auditUserId;
    /**
     * 备注
     */
    private String remark;

    // ========== 转账相关字段 ==========

    /**
     * 转账单编号
     *
     * 关联 PayTransferRespDTO 的 id 字段
     */
    private Long payTransferId;
    /**
     * 转账渠道
     *

     */
    private String transferChannelCode;
    /**
     * 转账成功时间
     */
    private LocalDateTime transferTime;
    /**
     * 转账错误提示
     */
    private String transferErrorMsg;

    // 手动添加getter方法以解决Lombok编译问题
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getFeePrice() {
        return feePrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getAccountQrCodeUrl() {
        return accountQrCodeUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public Long getAuditUserId() {
        return auditUserId;
    }

    public String getRemark() {
        return remark;
    }

    public Long getPayTransferId() {
        return payTransferId;
    }

    public String getTransferChannelCode() {
        return transferChannelCode;
    }

    public LocalDateTime getTransferTime() {
        return transferTime;
    }

    public String getTransferErrorMsg() {
        return transferErrorMsg;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setFeePrice(BigDecimal feePrice) {
        this.feePrice = feePrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setAccountQrCodeUrl(String accountQrCodeUrl) {
        this.accountQrCodeUrl = accountQrCodeUrl;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setPayTransferId(Long payTransferId) {
        this.payTransferId = payTransferId;
    }

    public void setTransferChannelCode(String transferChannelCode) {
        this.transferChannelCode = transferChannelCode;
    }

    public void setTransferTime(LocalDateTime transferTime) {
        this.transferTime = transferTime;
    }

    public void setTransferErrorMsg(String transferErrorMsg) {
        this.transferErrorMsg = transferErrorMsg;
    }

}