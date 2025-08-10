package cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
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
@TableName("trade_regional_agent_record")
@KeySequence("trade_regional_agent_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
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
     * 代理编号
     * <p>
     * 关联 RegionalAgentDO.id
     */
    private Long agentId;

    /**
     * 用户编号
     * <p>
     * 关联 MemberUserDO.id
     */
    private Long userId;

    /**
     * 业务编号
     */
    private String bizId;
    /**
     * 业务类型：1-订单，2-提现
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
     * 金额
     */
    private Integer price;
    /**
     * 当前总佣金
     */
    private Integer totalPrice;

    /**
     * 状态：0-待结算，1-已结算，2-已取消
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
     * 代理级别：1-省级，2-市级，3-县级
     */
    private Integer agentLevel;

    /**
     * 来源用户编号
     * <p>
     * 关联 MemberUserDO.id 字段，下单用户的编号
     */
    private Long sourceUserId;

    /**
     * 订单编号
     * <p>
     * 关联 TradeOrderDO.id
     */
    private Long orderId;

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

}