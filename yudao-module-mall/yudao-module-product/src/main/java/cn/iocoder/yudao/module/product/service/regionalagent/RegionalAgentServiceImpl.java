package cn.iocoder.yudao.module.product.service.regionalagent;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.ip.core.utils.AreaUtils;
import cn.iocoder.yudao.framework.ip.core.Area;
import cn.iocoder.yudao.framework.ip.core.enums.AreaTypeEnum;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentCreateReqVO;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentPageReqVO;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentUpdateReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentDO;
import cn.iocoder.yudao.module.product.dal.mysql.regionalagent.RegionalAgentMapper;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.product.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_NOT_EXISTS;

/**
 * 地区代理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class RegionalAgentServiceImpl implements RegionalAgentService {

    @Resource
    private RegionalAgentMapper regionalAgentMapper;

    @Resource
    private MemberUserApi memberUserApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRegionalAgent(RegionalAgentDO regionalAgent) {
        // 校验用户存在
        MemberUserRespDTO user = memberUserApi.getUser(regionalAgent.getUserId());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 校验地区存在
        Area area = AreaUtils.getArea(regionalAgent.getAreaId());
        if (area == null) {
            throw exception(REGIONAL_AGENT_AREA_NOT_EXISTS);
        }

        // 校验该用户在该地区是否已经是代理
        RegionalAgentDO existingAgent = regionalAgentMapper.selectByUserIdAndAreaId(regionalAgent.getUserId(), regionalAgent.getAreaId());
        if (existingAgent != null) {
            throw exception(REGIONAL_AGENT_ALREADY_EXISTS);
        }

        // 设置默认值
        regionalAgent.setAreaType(area.getType());
        regionalAgent.setAreaName(AreaUtils.format(regionalAgent.getAreaId()));
        regionalAgent.setStatus(RegionalAgentStatusEnum.APPLYING.getStatus());
        regionalAgent.setApplyTime(LocalDateTime.now());
        regionalAgent.setBrokeragePrice(0);
        regionalAgent.setFrozenBrokeragePrice(0);
        regionalAgentMapper.insert(regionalAgent);
        return regionalAgent.getId();
    }

    @Override
    public void updateRegionalAgent(RegionalAgentUpdateReqVO updateReqVO) {
        // 校验存在
        validateRegionalAgentExists(updateReqVO.getId());
        // 更新
        RegionalAgentDO updateObj = BeanUtils.toBean(updateReqVO, RegionalAgentDO.class);
        regionalAgentMapper.updateById(updateObj);
    }

    @Override
    public void deleteRegionalAgent(Long id) {
        // 校验存在
        validateRegionalAgentExists(id);
        // 删除
        regionalAgentMapper.deleteById(id);
    }

    private RegionalAgentDO validateRegionalAgentExists(Long id) {
        RegionalAgentDO regionalAgent = regionalAgentMapper.selectById(id);
        if (regionalAgent == null) {
            throw exception(REGIONAL_AGENT_NOT_EXISTS);
        }
        return regionalAgent;
    }

    @Override
    public RegionalAgentDO getRegionalAgent(Long id) {
        return regionalAgentMapper.selectById(id);
    }

    @Override
    public List<RegionalAgentDO> getRegionalAgentList(Collection<Long> ids) {
        return regionalAgentMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RegionalAgentDO> getRegionalAgentPage(RegionalAgentPageReqVO pageReqVO) {
        return regionalAgentMapper.selectPage(pageReqVO);
    }

    @Override
    public RegionalAgentDO getRegionalAgentByUserIdAndAreaId(Long userId, Integer areaId) {
        return regionalAgentMapper.selectByUserIdAndAreaId(userId, areaId);
    }

    @Override
    public List<RegionalAgentDO> getApprovedRegionalAgentsByAreaId(Integer areaId) {
        return regionalAgentMapper.selectListByAreaIdAndStatus(areaId, RegionalAgentStatusEnum.APPROVED.getStatus());
    }

    @Override
    public List<RegionalAgentDO> getApprovedRegionalAgentsByUserId(Long userId) {
        return regionalAgentMapper.selectListByUserIdAndStatus(userId, RegionalAgentStatusEnum.APPROVED.getStatus());
    }

    @Override
    public RegionalAgentDO getRegionalAgentByUserId(Long userId, RegionalAgentStatusEnum status) {
        List<RegionalAgentDO> agents = regionalAgentMapper.selectListByUserIdAndStatus(userId, status.getStatus());
        return CollUtil.isNotEmpty(agents) ? agents.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditRegionalAgent(Long id, Integer status, String auditReason) {
        // 校验存在
        RegionalAgentDO regionalAgent = validateRegionalAgentExists(id);
        
        // 校验状态
        if (!RegionalAgentStatusEnum.APPLYING.getStatus().equals(regionalAgent.getStatus())) {
            throw exception(REGIONAL_AGENT_STATUS_NOT_APPLYING);
        }

        // 更新状态
        RegionalAgentDO updateObj = new RegionalAgentDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        updateObj.setAuditReason(auditReason);
        updateObj.setAuditTime(LocalDateTime.now());
        
        if (RegionalAgentStatusEnum.APPROVED.getStatus().equals(status)) {
            updateObj.setAgentTime(LocalDateTime.now());
        }
        
        regionalAgentMapper.updateById(updateObj);
    }

    @Override
    public boolean updateAgentPrice(Long id, Integer price) {
        if (price == 0) {
            return true;
        }
        return regionalAgentMapper.updateBrokeragePrice(id, price) > 0;
    }

    @Override
    public void updateAgentFrozenPrice(Long id, Integer frozenPrice) {
        if (frozenPrice != 0) {
            regionalAgentMapper.updateFrozenBrokeragePrice(id, frozenPrice);
        }
    }

    @Override
    public void updateFrozenPriceDecrAndPriceIncr(Long id, Integer frozenPrice) {
        if (frozenPrice > 0) {
            regionalAgentMapper.updateFrozenPriceDecrAndPriceIncr(id, frozenPrice);
        }
    }

    @Override
    public List<RegionalAgentDO> getRegionalAgentsByDeliveryArea(Integer areaId) {
        List<RegionalAgentDO> agents = new ArrayList<>();
        
        // 获取地区信息
        Area area = AreaUtils.getArea(areaId);
        if (area == null) {
            return agents;
        }

        // 按层级查找代理：县级 -> 市级 -> 省级
        if (AreaTypeEnum.DISTRICT.getType().equals(area.getType())) {
            // 当前是县级，查找县级代理
            List<RegionalAgentDO> districtAgents = getApprovedRegionalAgentsByAreaId(areaId);
            agents.addAll(districtAgents);
            
            // 查找市级代理
            Integer cityId = AreaUtils.getParentIdByType(areaId, AreaTypeEnum.CITY);
            if (cityId != null) {
                List<RegionalAgentDO> cityAgents = getApprovedRegionalAgentsByAreaId(cityId);
                agents.addAll(cityAgents);
            }
            
            // 查找省级代理
            Integer provinceId = AreaUtils.getParentIdByType(areaId, AreaTypeEnum.PROVINCE);
            if (provinceId != null) {
                List<RegionalAgentDO> provinceAgents = getApprovedRegionalAgentsByAreaId(provinceId);
                agents.addAll(provinceAgents);
            }
        } else if (AreaTypeEnum.CITY.getType().equals(area.getType())) {
            // 当前是市级，查找市级代理
            List<RegionalAgentDO> cityAgents = getApprovedRegionalAgentsByAreaId(areaId);
            agents.addAll(cityAgents);
            
            // 查找省级代理
            Integer provinceId = AreaUtils.getParentIdByType(areaId, AreaTypeEnum.PROVINCE);
            if (provinceId != null) {
                List<RegionalAgentDO> provinceAgents = getApprovedRegionalAgentsByAreaId(provinceId);
                agents.addAll(provinceAgents);
            }
        } else if (AreaTypeEnum.PROVINCE.getType().equals(area.getType())) {
            // 当前是省级，查找省级代理
            List<RegionalAgentDO> provinceAgents = getApprovedRegionalAgentsByAreaId(areaId);
            agents.addAll(provinceAgents);
        }
        
        return agents;
    }

}