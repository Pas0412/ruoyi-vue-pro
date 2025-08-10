package cn.iocoder.yudao.module.trade.dal.mysql.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.record.RegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.record.AppRegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 地区代理佣金记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RegionalAgentRecordMapper extends BaseMapperX<RegionalAgentRecordDO> {

    default PageResult<RegionalAgentRecordDO> selectPage(RegionalAgentRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RegionalAgentRecordDO>()
                .eqIfPresent(RegionalAgentRecordDO::getAgentId, reqVO.getAgentId())
                .eqIfPresent(RegionalAgentRecordDO::getUserId, reqVO.getUserId())
                .eqIfPresent(RegionalAgentRecordDO::getBizType, reqVO.getBizType())
                .eqIfPresent(RegionalAgentRecordDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RegionalAgentRecordDO::getAgentLevel, reqVO.getAgentLevel())
                .betweenIfPresent(RegionalAgentRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegionalAgentRecordDO::getId));
    }

    default PageResult<RegionalAgentRecordDO> selectPage(AppRegionalAgentRecordPageReqVO reqVO, Long userId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RegionalAgentRecordDO>()
                .eq(RegionalAgentRecordDO::getUserId, userId)
                .eqIfPresent(RegionalAgentRecordDO::getBizType, reqVO.getBizType())
                .eqIfPresent(RegionalAgentRecordDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RegionalAgentRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegionalAgentRecordDO::getId));
    }

    default int updateByIdAndStatus(Long id, Integer status, RegionalAgentRecordDO updateObj) {
        return update(updateObj, new LambdaQueryWrapper<RegionalAgentRecordDO>()
                .eq(RegionalAgentRecordDO::getId, id)
                .eq(RegionalAgentRecordDO::getStatus, status));
    }

    default List<RegionalAgentRecordDO> selectListByBizTypeAndBizId(Integer bizType, String bizId) {
        return selectList(RegionalAgentRecordDO::getBizType, bizType,
                RegionalAgentRecordDO::getBizId, bizId);
    }

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
    @Select("SELECT IFNULL(SUM(price), 0) FROM trade_regional_agent_record " +
            "WHERE user_id = #{userId} AND biz_type = #{bizType} AND status = #{status} AND deleted = FALSE " +
            "AND create_time BETWEEN #{beginTime} AND #{endTime}")
    Integer selectPriceSummaryByUserIdAndBizTypeAndCreateTimeBetween(@Param("userId") Long userId,
                                                                     @Param("bizType") Integer bizType,
                                                                     @Param("status") Integer status,
                                                                     @Param("beginTime") LocalDateTime beginTime,
                                                                     @Param("endTime") LocalDateTime endTime);

    /**
     * 获取用户佣金统计
     *
     * @param userId 用户编号
     * @param bizType 业务类型
     * @param status 状态
     * @return 佣金金额
     */
    @Select("SELECT IFNULL(SUM(price), 0) FROM trade_regional_agent_record " +
            "WHERE user_id = #{userId} AND biz_type = #{bizType} AND status = #{status} AND deleted = FALSE")
    Integer selectPriceSummaryByUserIdAndBizTypeAndStatus(@Param("userId") Long userId,
                                                          @Param("bizType") Integer bizType,
                                                          @Param("status") Integer status);

    /**
     * 获取指定时间段内，解冻的佣金记录
     *
     * @return 佣金记录编号数组
     */
    default List<RegionalAgentRecordDO> selectListByUnfreezeTimeLe(LocalDateTime unfreezeTime) {
        return selectList(new LambdaQueryWrapperX<RegionalAgentRecordDO>()
                .eq(RegionalAgentRecordDO::getStatus, 0) // 待结算
                .le(RegionalAgentRecordDO::getUnfreezeTime, unfreezeTime));
    }

}