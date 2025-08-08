package cn.iocoder.yudao.module.trade.dal.mysql.areaagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.record.TradeAreaAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentRecordDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 地区代理佣金记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TradeAreaAgentRecordMapper extends BaseMapperX<TradeAreaAgentRecordDO> {

    default PageResult<TradeAreaAgentRecordDO> selectPage(TradeAreaAgentRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TradeAreaAgentRecordDO>()
                .eqIfPresent(TradeAreaAgentRecordDO::getUserId, reqVO.getUserId())
                .eqIfPresent(TradeAreaAgentRecordDO::getAreaLevel, reqVO.getAreaLevel())
                .eqIfPresent(TradeAreaAgentRecordDO::getBizType, reqVO.getBizType())
                .eqIfPresent(TradeAreaAgentRecordDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TradeAreaAgentRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TradeAreaAgentRecordDO::getId));
    }

    /**
     * 根据用户编号查询佣金记录列表
     *
     * @param userId 用户编号
     * @return 佣金记录列表
     */
    default List<TradeAreaAgentRecordDO> selectListByUserId(Long userId) {
        return selectList(TradeAreaAgentRecordDO::getUserId, userId);
    }

    /**
     * 根据业务编号和业务类型查询佣金记录列表
     *
     * @param bizId   业务编号
     * @param bizType 业务类型
     * @return 佣金记录列表
     */
    default List<TradeAreaAgentRecordDO> selectListByBiz(String bizId, Integer bizType) {
        return selectList(new LambdaQueryWrapperX<TradeAreaAgentRecordDO>()
                .eq(TradeAreaAgentRecordDO::getBizId, bizId)
                .eq(TradeAreaAgentRecordDO::getBizType, bizType));
    }

    /**
     * 根据状态和解冻时间查询佣金记录列表
     *
     * @param status 状态
     * @param unfreezeTime 解冻时间
     * @return 佣金记录列表
     */
    default List<TradeAreaAgentRecordDO> selectListByStatusAndUnfreezeTime(Integer status, LocalDateTime unfreezeTime) {
        return selectList(new LambdaQueryWrapper<TradeAreaAgentRecordDO>()
                .eq(TradeAreaAgentRecordDO::getStatus, status)
                .lt(TradeAreaAgentRecordDO::getUnfreezeTime, unfreezeTime));
    }

    /**
     * 根据编号和状态更新佣金记录
     *
     * @param id 编号
     * @param status 状态
     * @param updateObj 更新对象
     * @return 更新行数
     */
    default int updateByIdAndStatus(Long id, Integer status, TradeAreaAgentRecordDO updateObj) {
        return update(updateObj, new LambdaQueryWrapper<TradeAreaAgentRecordDO>()
                .eq(TradeAreaAgentRecordDO::getId, id)
                .eq(TradeAreaAgentRecordDO::getStatus, status));
    }

    /**
     * 根据用户编号汇总佣金
     *
     * @param userId 用户编号
     * @param status 状态
     * @return 佣金总额
     */
    Integer selectSummaryPriceByUserId(@Param("userId") Long userId, @Param("status") Integer status);

}