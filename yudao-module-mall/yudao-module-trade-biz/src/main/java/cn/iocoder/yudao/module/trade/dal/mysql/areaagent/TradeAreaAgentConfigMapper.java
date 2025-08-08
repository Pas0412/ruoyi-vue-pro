package cn.iocoder.yudao.module.trade.dal.mysql.areaagent;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentConfigDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地区代理配置 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TradeAreaAgentConfigMapper extends BaseMapperX<TradeAreaAgentConfigDO> {

    /**
     * 根据代理等级查询配置
     *
     * @param areaLevel 代理等级
     * @return 配置信息
     */
    default TradeAreaAgentConfigDO selectByAreaLevel(Integer areaLevel) {
        return selectOne(TradeAreaAgentConfigDO::getAreaLevel, areaLevel);
    }

    /**
     * 查询所有启用的配置
     *
     * @return 配置列表
     */
    default List<TradeAreaAgentConfigDO> selectListByEnabled() {
        return selectList(TradeAreaAgentConfigDO::getEnabled, true);
    }

}