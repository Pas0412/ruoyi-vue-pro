package cn.iocoder.yudao.module.trade.dal.mysql.regionalagent;

import cn.hutool.core.lang.Assert;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.agent.RegionalAgentPageReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent.RegionalAgentDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

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
                .eqIfPresent(RegionalAgentDO::getProvinceId, reqVO.getProvinceId())
                .eqIfPresent(RegionalAgentDO::getCityId, reqVO.getCityId())
                .eqIfPresent(RegionalAgentDO::getDistrictId, reqVO.getDistrictId())
                .eqIfPresent(RegionalAgentDO::getAgentLevel, reqVO.getAgentLevel())
                .eqIfPresent(RegionalAgentDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RegionalAgentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegionalAgentDO::getId));
    }

    /**
     * 根据地区和级别查询代理
     *
     * @param provinceId  省份编号
     * @param cityId      城市编号
     * @param districtId  区县编号
     * @param agentLevel  代理级别
     * @return 代理信息
     */
    default RegionalAgentDO selectByAreaAndLevel(Integer provinceId, Integer cityId, Integer districtId, Integer agentLevel) {
        LambdaQueryWrapperX<RegionalAgentDO> query = new LambdaQueryWrapperX<RegionalAgentDO>()
                .eq(RegionalAgentDO::getProvinceId, provinceId)
                .eq(RegionalAgentDO::getAgentLevel, agentLevel);
        
        if (agentLevel >= 2) { // 市级及以下需要匹配城市
            query.eq(RegionalAgentDO::getCityId, cityId);
        }
        if (agentLevel >= 3) { // 县级需要匹配区县
            query.eq(RegionalAgentDO::getDistrictId, districtId);
        }
        
        return selectOne(query);
    }

    /**
     * 根据用户编号查询代理信息
     *
     * @param userId 用户编号
     * @return 代理信息列表
     */
    default List<RegionalAgentDO> selectListByUserId(Long userId) {
        return selectList(RegionalAgentDO::getUserId, userId);
    }

    /**
     * 更新代理可用佣金（增加）
     *
     * @param id        代理编号
     * @param incrCount 增加佣金（正数）
     */
    default void updateAgentPriceIncr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount > 0);
        LambdaUpdateWrapper<RegionalAgentDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<RegionalAgentDO>()
                .eq(RegionalAgentDO::getId, id)
                .setSql("agent_price = agent_price + " + incrCount);
        update(null, lambdaUpdateWrapper);
    }

    /**
     * 更新代理可用佣金（减少）
     *
     * @param id        代理编号
     * @param decrCount 减少佣金（正数）
     * @return 更新条数
     */
    default int updateAgentPriceDecr(Long id, Integer decrCount) {
        Assert.isTrue(decrCount > 0);
        LambdaUpdateWrapper<RegionalAgentDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<RegionalAgentDO>()
                .eq(RegionalAgentDO::getId, id)
                .ge(RegionalAgentDO::getAgentPrice, decrCount) // 余额不足时，更新失败
                .setSql("agent_price = agent_price - " + decrCount);
        return update(null, lambdaUpdateWrapper);
    }

    /**
     * 更新代理冻结佣金（增加）
     *
     * @param id        代理编号
     * @param incrCount 增加冻结佣金（正数）
     */
    default void updateFrozenPriceIncr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount > 0);
        LambdaUpdateWrapper<RegionalAgentDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<RegionalAgentDO>()
                .eq(RegionalAgentDO::getId, id)
                .setSql("frozen_price = frozen_price + " + incrCount);
        update(null, lambdaUpdateWrapper);
    }

    /**
     * 更新代理冻结佣金（减少）
     *
     * @param id        代理编号
     * @param decrCount 减少冻结佣金（正数）
     */
    default void updateFrozenPriceDecr(Long id, Integer decrCount) {
        Assert.isTrue(decrCount > 0);
        LambdaUpdateWrapper<RegionalAgentDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<RegionalAgentDO>()
                .eq(RegionalAgentDO::getId, id)
                .setSql("frozen_price = frozen_price - " + decrCount);
        update(null, lambdaUpdateWrapper);
    }

    /**
     * 更新代理冻结佣金（减少）和可用佣金（增加）
     *
     * @param id        代理编号
     * @param incrCount 变更佣金（正数）
     * @return 更新条数
     */
    default int updateFrozenPriceDecrAndAgentPriceIncr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount > 0);
        LambdaUpdateWrapper<RegionalAgentDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<RegionalAgentDO>()
                .eq(RegionalAgentDO::getId, id)
                .ge(RegionalAgentDO::getFrozenPrice, incrCount) // 冻结佣金不足时，更新失败
                .setSql("frozen_price = frozen_price - " + incrCount + ", agent_price = agent_price + " + incrCount);
        return update(null, lambdaUpdateWrapper);
    }

}