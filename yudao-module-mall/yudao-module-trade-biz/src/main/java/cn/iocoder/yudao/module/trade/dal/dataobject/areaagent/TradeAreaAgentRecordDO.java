package cn.iocoder.yudao.module.trade.dal.dataobject.areaagent;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 地区代理佣金记录 DO
 *
 * @author 芋道源码
 */
@TableName("trade_area_agent_record")
@KeySequence("trade_area_agent_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeAreaAgentRecordDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 代理用户编号
     */
    private Long userId;
    /**
     * 代理地区编号
     */
    private Integer areaId;
    /**
     * 代理等级
     *
     * 枚举 {@link cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentLevelEnum}
     */
    private Integer areaLevel;
    /**
     * 业务编号（订单号等）
     */
    private String bizId;
    /**
     * 业务类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentRecordBizTypeEnum}
     */
    private Integer bizType;
    /**
     * 标题
     */
    private String title;
    /**
     * 佣金金额（分）
     */
    private Integer price;
    /**
     * 当前总佣金（分）
     */
    private Integer totalPrice;
    /**
     * 说明
     */
    private String description;
    /**
     * 状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentRecordStatusEnum}
     */
    private Integer status;
    /**
     * 冻结时间（天）
     */
    private Integer frozenDays;
    /**
     * 解冻时间
     */
    private LocalDateTime unfreezeTime;
    /**
     * 来源用户编号（下单用户）
     */
    private Long sourceUserId;
    /**
     * 订单收货地区编号
     */
    private Integer orderAreaId;

}