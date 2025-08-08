package cn.iocoder.yudao.module.trade.service.areaagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user.TradeAreaAgentUserCreateReqVO;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user.TradeAreaAgentUserPageReqVO;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user.TradeAreaAgentUserUpdateReqVO;
import cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.user.*;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentUserDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 地区代理用户 Service 接口
 *
 * @author 芋道源码
 */
public interface TradeAreaAgentUserService {

    /**
     * 创建地区代理用户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAreaAgentUser(@Valid TradeAreaAgentUserCreateReqVO createReqVO);

    /**
     * 更新地区代理用户
     *
     * @param updateReqVO 更新信息
     */
    void updateAreaAgentUser(@Valid TradeAreaAgentUserUpdateReqVO updateReqVO);

    /**
     * 删除地区代理用户
     *
     * @param id 编号
     */
    void deleteAreaAgentUser(Long id);

    /**
     * 获得地区代理用户
     *
     * @param id 编号
     * @return 地区代理用户
     */
    TradeAreaAgentUserDO getAreaAgentUser(Long id);

    /**
     * 获得地区代理用户列表
     *
     * @param ids 编号
     * @return 地区代理用户列表
     */
    List<TradeAreaAgentUserDO> getAreaAgentUserList(List<Long> ids);

    /**
     * 获得地区代理用户分页
     *
     * @param pageReqVO 分页查询
     * @return 地区代理用户分页
     */
    PageResult<TradeAreaAgentUserDO> getAreaAgentUserPage(TradeAreaAgentUserPageReqVO pageReqVO);

    /**
     * 根据用户编号获得地区代理用户
     *
     * @param userId 用户编号
     * @return 地区代理用户
     */
    TradeAreaAgentUserDO getAreaAgentUserByUserId(Long userId);

    /**
     * 根据地区编号获得地区代理用户
     *
     * @param areaId 地区编号
     * @return 地区代理用户
     */
    TradeAreaAgentUserDO getAreaAgentUserByAreaId(Integer areaId);

    /**
     * 根据地区编号列表获得地区代理用户列表
     *
     * @param areaIds 地区编号列表
     * @return 地区代理用户列表
     */
    List<TradeAreaAgentUserDO> getAreaAgentUserListByAreaIds(List<Integer> areaIds);

    /**
     * 更新地区代理用户佣金
     *
     * @param userId           用户编号
     * @param commissionPrice  可用佣金变化（分）
     * @param frozenPrice      冻结佣金变化（分）
     */
    void updateAreaAgentUserPrice(Long userId, Integer commissionPrice, Integer frozenPrice);

    /**
     * 启用或禁用地区代理用户
     *
     * @param id      编号
     * @param enabled 是否启用
     */
    void updateAreaAgentUserEnabled(Long id, Boolean enabled);

    // ========== App 相关方法 ==========

    /**
     * 根据用户编号获得地区代理用户（如果不存在则创建）
     *
     * @param userId 用户编号
     * @return 地区代理用户
     */
    TradeAreaAgentUserDO getOrCreateTradeAreaAgentUser(Long userId);

    /**
     * 根据用户编号获得地区代理用户
     *
     * @param userId 用户编号
     * @return 地区代理用户
     */
    TradeAreaAgentUserDO getTradeAreaAgentUserByUserId(Long userId);

    /**
     * 申请成为地区代理
     *
     * @param userId 用户编号
     * @param areaId 地区编号
     */
    void applyAreaAgent(Long userId, Long areaId);

    /**
     * 获得个人地区代理统计
     *
     * @param userId 用户编号
     * @return 统计信息
     */
    AppTradeAreaAgentUserSummaryRespVO getAreaAgentUserSummary(Long userId);

    /**
     * 获得下级代理分页
     *
     * @param pageReqVO 分页查询
     * @return 下级代理分页
     */
    PageResult<AppTradeAreaAgentUserChildRespVO> getAreaAgentUserChildPage(AppTradeAreaAgentUserChildPageReqVO pageReqVO);

    /**
     * 获得可申请的地区树
     *
     * @param userId 用户编号
     * @return 地区树
     */
    AppTradeAreaAgentUserAreaTreeRespVO getAreaAgentUserAreaTree(Long userId);

    /**
     * 检查地区是否可申请
     *
     * @param areaId 地区编号
     * @return 是否可申请
     */
    Boolean checkAreaAvailable(Long areaId);

}