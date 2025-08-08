package cn.iocoder.yudao.module.trade.dal.dataobject.areaagent;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 地区代理配置 DO
 *
 * @author 芋道源码
 */
@TableName("trade_area_agent_config")
@KeySequence("trade_area_agent_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeAreaAgentConfigDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 代理等级
     *
     * 枚举 {@link cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentLevelEnum}
     */
    private Integer areaLevel;
    /**
     * 佣金比例（%）
     */
    private BigDecimal commissionPercent;
    /**
     * 佣金冻结天数
     */
    private Integer frozenDays;
    /**
     * 是否启用
     */
    private Boolean enabled;

}