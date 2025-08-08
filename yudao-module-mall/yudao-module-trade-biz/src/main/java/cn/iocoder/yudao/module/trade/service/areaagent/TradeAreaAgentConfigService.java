package cn.iocoder.yudao.module.trade.service.areaagent;

import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.config.TradeAreaAgentConfigUpdateReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 地区代理配置 Service 接口
 *
 * @author 芋道源码
 */
public interface TradeAreaAgentConfigService {

    /**
     * 更新地区代理配置
     *
     * @param updateReqVO 更新信息
     */
    void updateAreaAgentConfig(@Valid TradeAreaAgentConfigUpdateReqVO updateReqVO);

    /**
     * 获得地区代理配置
     *
     * @param id 编号
     * @return 地区代理配置
     */
    TradeAreaAgentConfigDO getAreaAgentConfig(Long id);

    /**
     * 获得地区代理配置列表
     *
     * @return 地区代理配置列表
     */
    List<TradeAreaAgentConfigDO> getAreaAgentConfigList();

    /**
     * 根据代理等级获得地区代理配置
     *
     * @param areaLevel 代理等级
     * @return 地区代理配置
     */
    TradeAreaAgentConfigDO getAreaAgentConfigByLevel(Integer areaLevel);

    /**
     * 获得所有启用的地区代理配置
     *
     * @return 地区代理配置列表
     */
    List<TradeAreaAgentConfigDO> getEnabledAreaAgentConfigList();

    /**
     * 启用或禁用地区代理配置
     *
     * @param id      编号
     * @param enabled 是否启用
     */
    void updateAreaAgentConfigEnabled(Long id, Boolean enabled);

}