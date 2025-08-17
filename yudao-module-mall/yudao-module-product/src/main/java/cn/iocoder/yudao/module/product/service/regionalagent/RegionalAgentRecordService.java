package cn.iocoder.yudao.module.product.service.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentRecordBizTypeEnum;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentRecordStatusEnum;
import cn.iocoder.yudao.module.product.service.regionalagent.bo.RegionalAgentAddReqBO;
import cn.iocoder.yudao.module.product.service.regionalagent.bo.UserRegionalAgentSummaryRespBO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * 地区代理记录 Service 接口
 *
 * @author 芋道源码
 */
public interface RegionalAgentRecordService {

    /**
     * 获得地区代理记录
     *
     * @param id 编号
     * @return 地区代理记录
     */
    RegionalAgentRecordDO getRegionalAgentRecord(Long id);

    /**
     * 获得地区代理记录分页
     *
     * @param pageReqVO 分页查询
     * @return 地区代理记录分页
     */
    PageResult<RegionalAgentRecordDO> getRegionalAgentRecordPage(RegionalAgentRecordPageReqVO pageReqVO);

    /**
     * 增加地区代理佣金【多级分佣】
     *
     * @param userId  会员编号
     * @param areaId  地区编号
     * @param bizType 业务类型
     * @param list    请求参数列表
     */
    void addRegionalAgentBrokerage(Long userId, Integer areaId, RegionalAgentRecordBizTypeEnum bizType, @Valid List<RegionalAgentAddReqBO> list);

    /**
     * 增加地区代理佣金【只针对指定代理】
     *
     * @param agentId        代理编号
     * @param bizType        业务类型
     * @param bizId          业务编号
     * @param brokeragePrice 佣金
     * @param title          标题
     */
    void addRegionalAgentBrokerage(Long agentId, RegionalAgentRecordBizTypeEnum bizType, String bizId, BigDecimal brokeragePrice, String title);

    /**
     * 减少地区代理佣金【只针对指定代理】
     *
     * @param agentId        代理编号
     * @param bizType        业务类型
     * @param bizId          业务编号
     * @param brokeragePrice 佣金
     * @param title          标题
     */
    default void reduceRegionalAgentBrokerage(Long agentId, RegionalAgentRecordBizTypeEnum bizType, String bizId, BigDecimal brokeragePrice, String title) {
        addRegionalAgentBrokerage(agentId, bizType, bizId, brokeragePrice.negate(), title);
    }

    /**
     * 取消地区代理佣金：将佣金记录，状态修改为已失效
     *
     * @param bizType 业务类型
     * @param bizId   业务编号
     */
    void cancelRegionalAgentBrokerage(RegionalAgentRecordBizTypeEnum bizType, String bizId);

    /**
     * 解冻佣金：将待结算的佣金记录，状态修改为已结算
     *
     * @return 解冻佣金的数量
     */
    int unfreezeRecord();

    /**
     * 按照 userId，汇总每个用户的佣金
     *
     * @param userIds 用户编号
     * @param bizType 业务类型
     * @param status  佣金状态
     * @return 用户佣金汇总 List
     */
    List<UserRegionalAgentSummaryRespBO> getUserRegionalAgentSummaryListByUserId(Collection<Long> userIds,
                                                                                 Integer bizType, Integer status);

    /**
     * 按照 userId，汇总每个用户的佣金
     *
     * @param userIds 用户编号
     * @param bizType 业务类型
     * @param status  佣金状态
     * @return 用户佣金汇总 Map
     */
    default Map<Long, UserRegionalAgentSummaryRespBO> getUserRegionalAgentSummaryMapByUserId(Collection<Long> userIds,
                                                                                             Integer bizType, Integer status) {
        return convertMap(getUserRegionalAgentSummaryListByUserId(userIds, bizType, status),
                UserRegionalAgentSummaryRespBO::getUserId);
    }

    /**
     * 获得用户的佣金汇总
     *
     * @param userId    用户编号
     * @param bizType   业务类型
     * @param status    佣金状态
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 佣金汇总
     */
    Integer getSummaryPriceByUserId(Long userId, RegionalAgentRecordBizTypeEnum bizType, RegionalAgentRecordStatusEnum status,
                                    LocalDateTime beginTime, LocalDateTime endTime);

}