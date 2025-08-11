package cn.iocoder.yudao.module.product.dal.dataobject.regionalagent;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 地区代理 DO
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
     * 可用佣金，单位：分
     */
    private Integer brokeragePrice;
    /**
     * 冻结佣金，单位：分
     */
    private Integer frozenBrokeragePrice;
    /**
     * 成为代理时间
     */
    private LocalDateTime agentTime;

}