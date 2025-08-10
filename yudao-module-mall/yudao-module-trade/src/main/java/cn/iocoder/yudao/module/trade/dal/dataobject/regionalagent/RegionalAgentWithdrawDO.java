package cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 地区代理提现 DO
 *
 * @author 芋道源码
 */
@TableName("trade_regional_agent_withdraw")
@KeySequence("trade_regional_agent_withdraw_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
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
     * 提现金额，单位：分
     */
    private Integer price;
    /**
     * 提现手续费，单位：分
     */
    private Integer feePrice;
    /**
     * 当前总佣金，单位：分
     */
    private Integer totalPrice;
    /**
     * 提现类型
     */
    private Integer type;

    /**
     * 提现姓名
     */
    private String userName;
    /**
     * 提现账号
     */
    private String userAccount;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 开户地址
     */
    private String bankAddress;
    /**
     * 收款码
     */
    private String qrCodeUrl;

    /**
     * 状态：0-审核中，10-审核通过 20-审核不通过；11 - 提现成功；21-提现失败
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
     * 备注
     */
    private String remark;

    /**
     * 转账订单编号
     */
    private Long payTransferId;
    /**
     * 转账渠道
     */
    private String transferChannelCode;
    /**
     * 转账支付时间
     */
    private LocalDateTime transferTime;
    /**
     * 转账错误提示
     */
    private String transferErrorMsg;

}