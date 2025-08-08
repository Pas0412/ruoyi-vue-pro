package cn.iocoder.yudao.module.trade.service.areaagent;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.config.TradeAreaAgentConfigUpdateReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentConfigDO;
import cn.iocoder.yudao.module.trade.dal.mysql.areaagent.TradeAreaAgentConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.trade.enums.ErrorCodeConstants.AREA_AGENT_CONFIG_NOT_EXISTS;

/**
 * 地区代理配置 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TradeAreaAgentConfigServiceImpl implements TradeAreaAgentConfigService {

    @Resource
    private TradeAreaAgentConfigMapper areaAgentConfigMapper;

    @Override
    public void updateAreaAgentConfig(@Valid TradeAreaAgentConfigUpdateReqVO updateReqVO) {
        // 校验存在
        validateAreaAgentConfigExists(updateReqVO.getId());
        // 更新
        TradeAreaAgentConfigDO updateObj = BeanUtils.toBean(updateReqVO, TradeAreaAgentConfigDO.class);
        areaAgentConfigMapper.updateById(updateObj);
    }

    private void validateAreaAgentConfigExists(Long id) {
        if (areaAgentConfigMapper.selectById(id) == null) {
            throw exception(AREA_AGENT_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public TradeAreaAgentConfigDO getAreaAgentConfig(Long id) {
        return areaAgentConfigMapper.selectById(id);
    }

    @Override
    public List<TradeAreaAgentConfigDO> getAreaAgentConfigList() {
        return areaAgentConfigMapper.selectList();
    }

    @Override
    public TradeAreaAgentConfigDO getAreaAgentConfigByLevel(Integer areaLevel) {
        return areaAgentConfigMapper.selectByAreaLevel(areaLevel);
    }

    @Override
    public List<TradeAreaAgentConfigDO> getEnabledAreaAgentConfigList() {
        return areaAgentConfigMapper.selectListByEnabled();
    }

    @Override
    public void updateAreaAgentConfigEnabled(Long id, Boolean enabled) {
        // 校验存在
        validateAreaAgentConfigExists(id);
        // 更新
        TradeAreaAgentConfigDO updateObj = new TradeAreaAgentConfigDO();
        updateObj.setId(id);
        updateObj.setEnabled(enabled);
        areaAgentConfigMapper.updateById(updateObj);
    }

}