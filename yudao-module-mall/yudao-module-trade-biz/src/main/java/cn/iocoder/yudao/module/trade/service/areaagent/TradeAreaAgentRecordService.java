package cn.iocoder.yudao.module.trade.service.areaagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.record.TradeAreaAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.record.*;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentRecordDO;
import cn.iocoder.yudao.module.trade.dal.dataobject.order.TradeOrderDO;

import java.util.List;

/**
 * 地区代理佣金记录 Service 接口
 *
 * @author 芋道源码
 */
public interface TradeAreaAgentRecordService {

    /**
     * 获得地区代理佣金记录
     *
     * @param id 编号
     * @return 地区代理佣金记录
     */
    TradeAreaAgentRecordDO getAreaAgentRecord(Long id);

    /**
     * 获得地区代理佣金记录列表
     *
     * @param ids 编号
     * @return 地区代理佣金记录列表
     */
    List<TradeAreaAgentRecordDO> getAreaAgentRecordList(List<Long> ids);

    /**
     * 获得地区代理佣金记录分页
     *
     * @param pageReqVO 分页查询
     * @return 地区代理佣金记录分页
     */
    PageResult<TradeAreaAgentRecordDO> getAreaAgentRecordPage(TradeAreaAgentRecordPageReqVO pageReqVO);

    /**
     * 根据用户编号获得地区代理佣金记录列表
     *
     * @param userId 用户编号
     * @return 地区代理佣金记录列表
     */
    List<TradeAreaAgentRecordDO> getAreaAgentRecordListByUserId(Long userId);

    /**
     * 添加地区代理佣金
     * 根据订单信息计算并添加地区代理佣金记录
     *
     * @param order 订单信息
     */
    void addAreaAgentCommission(TradeOrderDO order);

    /**
     * 取消地区代理佣金
     * 当订单取消或退款时，取消对应的佣金记录
     *
     * @param bizId   业务编号（订单号）
     * @param bizType 业务类型
     */
    void cancelAreaAgentCommission(String bizId, Integer bizType);

    /**
     * 解冻地区代理佣金
     * 将到期的冻结佣金转为可用佣金
     *
     * @return 解冻佣金的数量
     */
    int unfreezeAreaAgentCommission();

    /**
     * 根据用户编号汇总佣金
     *
     * @param userId 用户编号
     * @param status 状态
     * @return 佣金总额
     */
    Integer getSummaryPriceByUserId(Long userId, Integer status);

    // ========== App 相关方法 ==========

    /**
     * 获得地区代理佣金记录分页（App）
     *
     * @param pageReqVO 分页查询
     * @return 地区代理佣金记录分页
     */
    PageResult<TradeAreaAgentRecordDO> getTradeAreaAgentRecordPage(AppTradeAreaAgentRecordPageReqVO pageReqVO);

    /**
     * 获得个人地区代理佣金汇总
     *
     * @param userId 用户编号
     * @return 佣金汇总
     */
    AppTradeAreaAgentRecordSummaryRespVO getAreaAgentRecordSummary(Long userId);

    /**
     * 获得个人地区代理月度佣金汇总
     *
     * @param userId 用户编号
     * @param year   年份
     * @return 月度佣金汇总
     */
    AppTradeAreaAgentRecordMonthlySummaryRespVO getAreaAgentRecordMonthlySummary(Long userId, Integer year);

    /**
     * 获得个人地区代理佣金趋势
     *
     * @param userId 用户编号
     * @param days   天数
     * @return 佣金趋势
     */
    AppTradeAreaAgentRecordTrendRespVO getAreaAgentRecordTrend(Long userId, Integer days);

}