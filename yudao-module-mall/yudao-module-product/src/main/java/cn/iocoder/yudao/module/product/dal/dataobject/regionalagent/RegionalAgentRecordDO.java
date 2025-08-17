package cn.iocoder.yudao.module.product.dal.dataobject.regionalagent;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 地区代理佣金记录 DO
 *
 * @author 芋道源码
 */
@TableName("product_regional_agent_record")
@KeySequence("product_regional_agent_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionalAgentRecordDO extends BaseDO {

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
     * 业务编号
     *
     * 例如说，关联的订单编号
     */
    private String bizId;
    /**
     * 业务类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentRecordBizTypeEnum}
     */
    private Integer bizType;
    /**
     * 标题
     */
    private String title;
    /**
     * 说明
     */
    private String description;
    /**
     * 金额，单位：元
     *
     * 正数，表示增加
     * 负数，表示减少
     */
    private BigDecimal price;
    /**
     * 当前总佣金，单位：元
     */
    private BigDecimal totalPrice;
    /**
     * 状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentRecordStatusEnum}
     */
    private Integer status;
    /**
     * 冻结时间
     */
    private LocalDateTime frozenTime;
    /**
     * 解冻时间
     */
    private LocalDateTime unfreezeTime;
    /**
     * 来源用户等级
     *
     * 1-省级代理，2-市级代理，3-县级代理
     */
    private Integer sourceUserLevel;
    /**
     * 来源用户编号
     *
     * 关联 MemberUserDO 的 id 字段
     */
    private Long sourceUserId;

    // 手动添加getter方法以解决Lombok编译问题
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getBizId() {
        return bizId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime getFrozenTime() {
        return frozenTime;
    }

    public LocalDateTime getUnfreezeTime() {
        return unfreezeTime;
    }

    public Integer getSourceUserLevel() {
        return sourceUserLevel;
    }

    public Long getSourceUserId() {
        return sourceUserId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setFrozenTime(LocalDateTime frozenTime) {
        this.frozenTime = frozenTime;
    }

    public void setUnfreezeTime(LocalDateTime unfreezeTime) {
        this.unfreezeTime = unfreezeTime;
    }

    public void setSourceUserLevel(Integer sourceUserLevel) {
        this.sourceUserLevel = sourceUserLevel;
    }

    public void setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

}