package cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent;

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
@TableName("trade_regional_agent")
@KeySequence("trade_regional_agent_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
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
     * <p>
     * 对应 MemberUserDO 的 id 字段
     */
    private Long userId;

    /**
     * 省份编号
     */
    private Integer provinceId;
    /**
     * 城市编号
     */
    private Integer cityId;
    /**
     * 区县编号
     */
    private Integer districtId;

    /**
     * 代理级别：1-省级，2-市级，3-县级
     */
    private Integer agentLevel;

    /**
     * 状态：0-待审核，1-已通过，2-已拒绝，3-已禁用
     */
    private Integer status;

    /**
     * 申请理由
     */
    private String applyReason;
    /**
     * 审核理由
     */
    private String auditReason;
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
    /**
     * 审核人编号
     */
    private Long auditUserId;

    /**
     * 可用佣金
     */
    private Integer agentPrice;
    /**
     * 冻结佣金
     */
    private Integer frozenPrice;

}