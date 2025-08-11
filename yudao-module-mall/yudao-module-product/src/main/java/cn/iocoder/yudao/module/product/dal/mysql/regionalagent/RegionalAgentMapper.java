package cn.iocoder.yudao.module.product.dal.mysql.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentPageReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 地区代理 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RegionalAgentMapper extends BaseMapperX<RegionalAgentDO> {

    default PageResult<RegionalAgentDO> selectPage(RegionalAgentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RegionalAgentDO>()
                .eqIfPresent(RegionalAgentDO::getUserId, reqVO.getUserId())
                .eqIfPresent(RegionalAgentDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RegionalAgentDO::getAreaType, reqVO.getAreaType())
                .likeIfPresent(RegionalAgentDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(RegionalAgentDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RegionalAgentDO::getApplyTime, reqVO.getApplyTime())
                .betweenIfPresent(RegionalAgentDO::getAuditTime, reqVO.getAuditTime())
                .orderByDesc(RegionalAgentDO::getId));
    }

    default RegionalAgentDO selectByUserIdAndAreaId(Long userId, Integer areaId) {
        return selectOne(new LambdaQueryWrapperX<RegionalAgentDO>()
                .eq(RegionalAgentDO::getUserId, userId)
                .eq(RegionalAgentDO::getAreaId, areaId));
    }

    default List<RegionalAgentDO> selectListByAreaIdAndStatus(Integer areaId, Integer status) {
        return selectList(new LambdaQueryWrapperX<RegionalAgentDO>()
                .eq(RegionalAgentDO::getAreaId, areaId)
                .eq(RegionalAgentDO::getStatus, status));
    }

    default List<RegionalAgentDO> selectListByUserIdAndStatus(Long userId, Integer status) {
        return selectList(new LambdaQueryWrapperX<RegionalAgentDO>()
                .eq(RegionalAgentDO::getUserId, userId)
                .eq(RegionalAgentDO::getStatus, status));
    }

    @Update("UPDATE product_regional_agent SET brokerage_price = brokerage_price + #{price} WHERE id = #{id}")
    int updateBrokeragePrice(@Param("id") Long id, @Param("price") Integer price);

    @Update("UPDATE product_regional_agent SET frozen_brokerage_price = frozen_brokerage_price + #{price} WHERE id = #{id}")
    int updateFrozenBrokeragePrice(@Param("id") Long id, @Param("price") Integer price);

    @Update("UPDATE product_regional_agent SET frozen_brokerage_price = frozen_brokerage_price - #{frozenPrice}, brokerage_price = brokerage_price + #{frozenPrice} WHERE id = #{id}")
    int updateFrozenPriceDecrAndPriceIncr(@Param("id") Long id, @Param("frozenPrice") Integer frozenPrice);

}