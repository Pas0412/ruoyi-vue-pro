package cn.iocoder.yudao.module.product.service.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentCreateReqVO;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentPageReqVO;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentUpdateReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentDO;
import cn.iocoder.yudao.module.product.enums.regionalagent.RegionalAgentStatusEnum;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 地区代理 Service 接口
 *
 * @author 芋道源码
 */
public interface RegionalAgentService {

    /**
     * 创建地区代理
     *
     * @param regionalAgent 创建信息
     * @return 编号
     */
    Long createRegionalAgent(@Valid RegionalAgentDO regionalAgent);

    /**
     * 更新地区代理
     *
     * @param updateReqVO 更新信息
     */
    void updateRegionalAgent(@Valid RegionalAgentUpdateReqVO updateReqVO);

    /**
     * 删除地区代理
     *
     * @param id 编号
     */
    void deleteRegionalAgent(Long id);

    /**
     * 获得地区代理
     *
     * @param id 编号
     * @return 地区代理
     */
    RegionalAgentDO getRegionalAgent(Long id);

    /**
     * 获得地区代理列表
     *
     * @param ids 编号列表
     * @return 地区代理列表
     */
    List<RegionalAgentDO> getRegionalAgentList(Collection<Long> ids);

    /**
     * 获得地区代理分页
     *
     * @param pageReqVO 分页查询
     * @return 地区代理分页
     */
    PageResult<RegionalAgentDO> getRegionalAgentPage(RegionalAgentPageReqVO pageReqVO);

    /**
     * 根据用户编号和地区编号获取地区代理
     *
     * @param userId 用户编号
     * @param areaId 地区编号
     * @return 地区代理
     */
    RegionalAgentDO getRegionalAgentByUserIdAndAreaId(Long userId, Integer areaId);

    /**
     * 根据地区编号获取已通过的代理列表
     *
     * @param areaId 地区编号
     * @return 代理列表
     */
    List<RegionalAgentDO> getApprovedRegionalAgentsByAreaId(Integer areaId);

    /**
     * 根据用户编号获取已通过的代理列表
     *
     * @param userId 用户编号
     * @return 代理列表
     */
    List<RegionalAgentDO> getApprovedRegionalAgentsByUserId(Long userId);

    /**
     * 根据用户编号和状态获取地区代理
     *
     * @param userId 用户编号
     * @param status 代理状态
     * @return 地区代理
     */
    RegionalAgentDO getRegionalAgentByUserId(Long userId, RegionalAgentStatusEnum status);

    /**
     * 审核地区代理申请
     *
     * @param id          代理编号
     * @param status      审核状态
     * @param auditReason 审核原因
     */
    void auditRegionalAgent(Long id, Integer status, String auditReason);

    /**
     * 更新代理佣金
     *
     * @param id    代理编号
     * @param price 佣金变动金额
     * @return 是否更新成功
     */
    boolean updateAgentPrice(Long id, Integer price);

    /**
     * 更新代理冻结佣金
     *
     * @param id          代理编号
     * @param frozenPrice 冻结佣金变动金额
     */
    void updateAgentFrozenPrice(Long id, Integer frozenPrice);

    /**
     * 冻结佣金减少，可用佣金增加
     *
     * @param id          代理编号
     * @param frozenPrice 冻结佣金金额
     */
    void updateFrozenPriceDecrAndPriceIncr(Long id, Integer frozenPrice);

    /**
     * 根据收货地址获取对应的地区代理
     *
     * @param areaId 地区编号
     * @return 地区代理（按级别排序：省->市->县）
     */
    List<RegionalAgentDO> getRegionalAgentsByDeliveryArea(Integer areaId);

}