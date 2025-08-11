package cn.iocoder.yudao.module.product.dal.mysql.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import cn.iocoder.yudao.module.product.service.regionalagent.bo.UserRegionalAgentSummaryRespBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 地区代理记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RegionalAgentRecordMapper extends BaseMapperX<RegionalAgentRecordDO> {

    default PageResult<RegionalAgentRecordDO> selectPage(RegionalAgentRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RegionalAgentRecordDO>()
                .eqIfPresent(RegionalAgentRecordDO::getUserId, reqVO.getUserId())
                .eqIfPresent(RegionalAgentRecordDO::getBizType, reqVO.getBizType())
                .eqIfPresent(RegionalAgentRecordDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RegionalAgentRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegionalAgentRecordDO::getId));
    }

    default List<RegionalAgentRecordDO> selectListByBizTypeAndBizId(Integer bizType, String bizId) {
        return selectList(new LambdaQueryWrapperX<RegionalAgentRecordDO>()
                .eq(RegionalAgentRecordDO::getBizType, bizType)
                .eq(RegionalAgentRecordDO::getBizId, bizId));
    }

    @Update("UPDATE product_regional_agent_record SET status = #{updateObj.status} WHERE id = #{id} AND status = #{status}")
    int updateByIdAndStatus(@Param("id") Long id, @Param("status") Integer status, @Param("updateObj") RegionalAgentRecordDO updateObj);

    @Select("SELECT COUNT(*) FROM product_regional_agent_record WHERE unfreeze_time <= #{unfreezeTime} AND status = 0")
    Long selectCountByUnfreezeTimeAndStatus(@Param("unfreezeTime") LocalDateTime unfreezeTime);

    @Select("SELECT * FROM product_regional_agent_record WHERE unfreeze_time <= #{unfreezeTime} AND status = 0 LIMIT #{limit}")
    List<RegionalAgentRecordDO> selectListByUnfreezeTimeAndStatus(@Param("unfreezeTime") LocalDateTime unfreezeTime, @Param("limit") Integer limit);

    @Select("SELECT user_id, SUM(price) AS price, COUNT(1) AS count FROM product_regional_agent_record " +
            "WHERE user_id IN (#{userIds}) AND biz_type = #{bizType} AND status = #{status} GROUP BY user_id")
    List<UserRegionalAgentSummaryRespBO> selectSummaryListByUserIds(@Param("userIds") Collection<Long> userIds,
                                                                    @Param("bizType") Integer bizType,
                                                                    @Param("status") Integer status);

    @Select("SELECT IFNULL(SUM(price), 0) FROM product_regional_agent_record " +
            "WHERE user_id = #{userId} AND biz_type = #{bizType} AND status = #{status} " +
            "AND create_time BETWEEN #{beginTime} AND #{endTime}")
    Integer selectSummaryPriceByUserIdAndBizTypeAndStatusAndCreateTimeBetween(@Param("userId") Long userId,
                                                                              @Param("bizType") Integer bizType,
                                                                              @Param("status") Integer status,
                                                                              @Param("beginTime") LocalDateTime beginTime,
                                                                              @Param("endTime") LocalDateTime endTime);

}