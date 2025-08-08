package cn.iocoder.yudao.module.trade.dal.mysql.areaagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user.TradeAreaAgentUserPageReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentUserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地区代理用户 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TradeAreaAgentUserMapper extends BaseMapperX<TradeAreaAgentUserDO> {

    default PageResult<TradeAreaAgentUserDO> selectPage(TradeAreaAgentUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TradeAreaAgentUserDO>()
                .eqIfPresent(TradeAreaAgentUserDO::getUserId, reqVO.getUserId())
                .eqIfPresent(TradeAreaAgentUserDO::getAreaLevel, reqVO.getAreaLevel())
                .likeIfPresent(TradeAreaAgentUserDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(TradeAreaAgentUserDO::getAgentEnabled, reqVO.getAgentEnabled())
                .betweenIfPresent(TradeAreaAgentUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TradeAreaAgentUserDO::getId));
    }

    /**
     * 根据用户编号查询地区代理用户
     *
     * @param userId 用户编号
     * @return 地区代理用户
     */
    default TradeAreaAgentUserDO selectByUserId(Long userId) {
        return selectOne(TradeAreaAgentUserDO::getUserId, userId);
    }

    /**
     * 根据地区编号查询地区代理用户
     *
     * @param areaId 地区编号
     * @return 地区代理用户
     */
    default TradeAreaAgentUserDO selectByAreaId(Integer areaId) {
        return selectOne(TradeAreaAgentUserDO::getAreaId, areaId);
    }

    /**
     * 根据地区编号列表查询地区代理用户列表
     *
     * @param areaIds 地区编号列表
     * @return 地区代理用户列表
     */
    default List<TradeAreaAgentUserDO> selectListByAreaIds(List<Integer> areaIds) {
        return selectList("area_id", areaIds);
    }

    /**
     * 根据代理等级查询地区代理用户列表
     *
     * @param areaLevel 代理等级
     * @return 地区代理用户列表
     */
    default List<TradeAreaAgentUserDO> selectListByAreaLevel(Integer areaLevel) {
        return selectList(TradeAreaAgentUserDO::getAreaLevel, areaLevel);
    }

}