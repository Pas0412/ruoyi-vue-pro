package cn.iocoder.yudao.module.trade.service.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.record.RegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.record.AppRegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import cn.iocoder.yudao.module.trade.enums.regionalagent.RegionalAgentRecordBizTypeEnum;

import java.util.Collection;
import java.util.List;

/**
 * 地区代理佣金记录 Service 接口
 *
 * @author 芋道源码
 */
public interface RegionalAgentRecordService {

    /**
     * 获得地区代理佣金记录
     *
     * @param id 编号
     * @return 地区代理佣金记录
     */
    RegionalAgentRecordDO getRegionalAgentRecord(Long id);

    /**
     * 获得地区代理佣金记录分页
     *
     * @param pageReqVO 分页查询
     * @return 地区代理佣金记录分页
     */
    PageResult<RegionalAgentRecordDO> getRegionalAgentRecordPage(RegionalAgentRecordPageReqVO pageReqVO);

    /**
     * 获得地区代理佣金记录分页（App）
     *
     * @param pageReqVO 分页查询
     * @param userId    用户编号
     * @return 地区代理佣金记录分页
     */
    PageResult<RegionalAgentRecordDO> getRegionalAgentRecordPage(AppRegionalAgentRecordPageReqVO pageReqVO, Long userId);

    /**
     * 增加地区代理佣金【只针对自己】
     *
     * @param userId         会员编号
     * @param bizType        业务类型
     * @param bizId          业务编号
     * @param agentPrice     佣金
     * @param title          标题
     */
    void addRegionalAgentRecord(Long userId, RegionalAgentRecordBizTypeEnum bizType, String bizId, Integer agentPrice, String title);

    /**
     * 减少地区代理佣金【只针对自己】
     *
     * @param userId         会员编号
     * @param bizType        业务类型
     * @param bizId          业务编号
     * @param agentPrice     佣金
     * @param title          标题
     */
    default void reduceRegionalAgentRecord(Long userId, RegionalAgentRecordBizTypeEnum bizType, String bizId, Integer agentPrice, String title) {
        addRegionalAgentRecord(userId, bizType, bizId, -agentPrice, title);
    }

    /**
     * 取消地区代理佣金：将佣金记录，状态修改为已失效
     *
     * @param bizType 业务类型
     * @param bizId   业务编号
     */
    void cancelRegionalAgentRecord(RegionalAgentRecordBizTypeEnum bizType, String bizId);

    /**
     * 创建地区代理佣金记录（支持冻结功能）
     *
     * @param agentId        代理编号
     * @param userId         用户编号
     * @param bizType        业务类型
     * @param bizId          业务编号
     * @param agentPrice     佣金金额
     * @param title          标题
     * @param description    描述
     * @param frozenDays     冻结天数
     * @param agentLevel     代理级别
     * @param sourceUserId   来源用户编号
     */
    void createRegionalAgentRecord(Long agentId, Long userId, RegionalAgentRecordBizTypeEnum bizType, String bizId, 
                                  Integer agentPrice, String title, String description, Integer frozenDays, 
                                  Integer agentLevel, Long sourceUserId);

    /**
     * 解冻地区代理佣金：将待结算的佣金记录，状态修改为已结算
     *
     * @return 解冻佣金的数量
     */
    int unfreezeRecord();

    /**
     * 获取用户昨日佣金
     *
     * @param userId 用户编号
     * @param bizType 业务类型
     * @param status 状态
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 佣金金额
     */
    Integer getYesterdayPrice(Long userId, Integer bizType, Integer status, java.time.LocalDateTime beginTime, java.time.LocalDateTime endTime);

    /**
     * 获取用户佣金统计
     *
     * @param userId 用户编号
     * @param bizType 业务类型
     * @param status 状态
     * @return 佣金金额
     */
    Integer getTotalPrice(Long userId, Integer bizType, Integer status);

}