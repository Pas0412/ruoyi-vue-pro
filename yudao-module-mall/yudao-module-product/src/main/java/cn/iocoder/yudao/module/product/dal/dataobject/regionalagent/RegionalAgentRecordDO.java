package cn.iocoder.yudao.module.product.dal.dataobject.regionalagent;

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
     * 金额，单位：分
     *
     * 正数，表示增加
     * 负数，表示减少
     */
    private Integer price;
    /**
     * 当前总佣金，单位：分
     */
    private Integer totalPrice;
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

}