package cn.iocoder.yudao.module.trade.service.areaagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.api.area.SystemAreaApi;
import cn.iocoder.yudao.module.system.api.area.dto.SystemAreaRespDTO;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user.TradeAreaAgentUserCreateReqVO;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user.TradeAreaAgentUserPageReqVO;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user.TradeAreaAgentUserUpdateReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentUserDO;
import cn.iocoder.yudao.module.trade.dal.mysql.areaagent.TradeAreaAgentUserMapper;
import cn.iocoder.yudao.module.trade.enums.areaagent.AreaAgentLevelEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.trade.enums.ErrorCodeConstants.*;

/**
 * 地区代理用户 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TradeAreaAgentUserServiceImpl implements TradeAreaAgentUserService {

    @Resource
    private TradeAreaAgentUserMapper areaAgentUserMapper;

    @Resource
    private SystemAreaApi systemAreaApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAreaAgentUser(@Valid TradeAreaAgentUserCreateReqVO createReqVO) {
        // 校验地区是否存在
        SystemAreaRespDTO area = systemAreaApi.getArea(createReqVO.getAreaId());
        if (area == null) {
            throw exception(AREA_NOT_EXISTS);
        }

        // 校验该地区是否已有代理
        TradeAreaAgentUserDO existsUser = areaAgentUserMapper.selectByAreaId(createReqVO.getAreaId());
        if (existsUser != null) {
            throw exception(AREA_AGENT_USER_EXISTS);
        }

        // 校验用户是否已是其他地区代理
        TradeAreaAgentUserDO existsUserByUserId = areaAgentUserMapper.selectByUserId(createReqVO.getUserId());
        if (existsUserByUserId != null) {
            throw exception(USER_ALREADY_AREA_AGENT);
        }

        // 插入
        TradeAreaAgentUserDO areaAgentUser = BeanUtils.toBean(createReqVO, TradeAreaAgentUserDO.class);
        areaAgentUser.setAreaLevel(area.getType());
        areaAgentUser.setAreaName(area.getName());
        areaAgentUser.setParentAreaId(area.getParentId());
        areaAgentUser.setEnabled(true);
        areaAgentUser.setAgentTime(LocalDateTime.now());
        areaAgentUser.setCommissionPrice(0);
        areaAgentUser.setFrozenPrice(0);
        areaAgentUserMapper.insert(areaAgentUser);
        return areaAgentUser.getId();
    }

    @Override
    public void updateAreaAgentUser(@Valid TradeAreaAgentUserUpdateReqVO updateReqVO) {
        // 校验存在
        validateAreaAgentUserExists(updateReqVO.getId());
        // 更新
        TradeAreaAgentUserDO updateObj = BeanUtils.toBean(updateReqVO, TradeAreaAgentUserDO.class);
        areaAgentUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteAreaAgentUser(Long id) {
        // 校验存在
        validateAreaAgentUserExists(id);
        // 删除
        areaAgentUserMapper.deleteById(id);
    }

    private void validateAreaAgentUserExists(Long id) {
        if (areaAgentUserMapper.selectById(id) == null) {
            throw exception(AREA_AGENT_USER_NOT_EXISTS);
        }
    }

    @Override
    public TradeAreaAgentUserDO getAreaAgentUser(Long id) {
        return areaAgentUserMapper.selectById(id);
    }

    @Override
    public List<TradeAreaAgentUserDO> getAreaAgentUserList(List<Long> ids) {
        return areaAgentUserMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TradeAreaAgentUserDO> getAreaAgentUserPage(TradeAreaAgentUserPageReqVO pageReqVO) {
        return areaAgentUserMapper.selectPage(pageReqVO);
    }

    @Override
    public TradeAreaAgentUserDO getAreaAgentUserByUserId(Long userId) {
        return areaAgentUserMapper.selectByUserId(userId);
    }

    @Override
    public TradeAreaAgentUserDO getAreaAgentUserByAreaId(Integer areaId) {
        return areaAgentUserMapper.selectByAreaId(areaId);
    }

    @Override
    public List<TradeAreaAgentUserDO> getAreaAgentUserListByAreaIds(List<Integer> areaIds) {
        return areaAgentUserMapper.selectListByAreaIds(areaIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAreaAgentUserPrice(Long userId, Integer commissionPrice, Integer frozenPrice) {
        TradeAreaAgentUserDO areaAgentUser = areaAgentUserMapper.selectByUserId(userId);
        if (areaAgentUser == null) {
            return;
        }

        // 更新佣金
        TradeAreaAgentUserDO updateObj = new TradeAreaAgentUserDO();
        updateObj.setId(areaAgentUser.getId());
        updateObj.setCommissionPrice(areaAgentUser.getCommissionPrice() + commissionPrice);
        updateObj.setFrozenPrice(areaAgentUser.getFrozenPrice() + frozenPrice);
        areaAgentUserMapper.updateById(updateObj);
    }

    @Override
    public void updateAreaAgentUserEnabled(Long id, Boolean enabled) {
        // 校验存在
        validateAreaAgentUserExists(id);
        // 更新
        TradeAreaAgentUserDO updateObj = new TradeAreaAgentUserDO();
        updateObj.setId(id);
        updateObj.setEnabled(enabled);
        areaAgentUserMapper.updateById(updateObj);
    }

}