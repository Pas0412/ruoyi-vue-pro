package cn.iocoder.yudao.module.trade.dal.dataobject.areaagent;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 地区代理用户 DO
 *
 * @author 芋道源码
 */
@TableName("trade_area_agent_user")
@KeySequence("trade_area_agent_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeAreaAgentUserDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 代理地区编号（对应system_area中的id）
     */
    private Integer areaId;
    /**
     * 代理等级
     *
     * 枚举 {@link cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentLevelEnum}
     */
    private Integer areaLevel;
    /**
     * 地区名称
     */
    private String areaName;
    /**
     * 上级地区编号
     */
    private Integer parentAreaId;
    /**
     * 是否启用代理资格
     */
    private Boolean agentEnabled;
    /**
     * 成为代理时间
     */
    private LocalDateTime agentTime;
    /**
     * 可用佣金（分）
     */
    private Integer commissionPrice;
    /**
     * 冻结佣金（分）
     */
    private Integer frozenPrice;

}