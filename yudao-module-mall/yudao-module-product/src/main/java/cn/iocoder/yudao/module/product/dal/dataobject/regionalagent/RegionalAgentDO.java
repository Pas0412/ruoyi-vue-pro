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
 * 区域代理 DO
 *
 * @author 芋道源码
 */
@TableName("product_regional_agent")
@KeySequence("product_regional_agent_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionalAgentDO extends BaseDO {

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
     * 地区编号
     *
     * 关联 Area 的 id 字段
     */
    private Integer areaId;
    /**
     * 地区类型
     *
     * 枚举 {@link cn.iocoder.yudao.framework.ip.core.enums.AreaTypeEnum}
     * 2-省份，3-城市，4-地区
     */
    private Integer areaType;
    /**
     * 地区名称
     */
    private String areaName;
    /**
     * 代理状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentStatusEnum}
     */
    private Integer status;
    /**
     * 申请时间
     */
    private LocalDateTime applyTime;
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
    /**
     * 审核人编号
     *
     * 关联 AdminUserDO 的 id 字段
     */
    private Long auditUserId;
    /**
     * 审核备注
     */
    private String auditReason;
    /**
     * 可用佣金，单位：元
     */
    private BigDecimal brokeragePrice;
    /**
     * 冻结佣金，单位：元
     */
    private BigDecimal frozenBrokeragePrice;
    /**
     * 成为代理时间
     */
    private LocalDateTime agentTime;

    // 手动添加getter方法以解决Lombok编译问题
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public String getAreaName() {
        return areaName;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public Long getAuditUserId() {
        return auditUserId;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public BigDecimal getBrokeragePrice() {
        return brokeragePrice;
    }

    public BigDecimal getFrozenBrokeragePrice() {
        return frozenBrokeragePrice;
    }

    public LocalDateTime getAgentTime() {
        return agentTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public void setBrokeragePrice(BigDecimal brokeragePrice) {
        this.brokeragePrice = brokeragePrice;
    }

    public void setFrozenBrokeragePrice(BigDecimal frozenBrokeragePrice) {
        this.frozenBrokeragePrice = frozenBrokeragePrice;
    }

    public void setAgentTime(LocalDateTime agentTime) {
        this.agentTime = agentTime;
    }

}