package cn.iocoder.yudao.module.product.service.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentWithdrawPageReqVO;
import cn.iocoder.yudao.module.product.controller.app.regionalagent.vo.AppRegionalAgentWithdrawCreateReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentWithdrawDO;

import javax.validation.Valid;

/**
 * 地区代理提现 Service 接口
 *
 * @author 芋道源码
 */
public interface RegionalAgentWithdrawService {

    /**
     * 获得地区代理提现
     *
     * @param id 编号
     * @return 地区代理提现
     */
    RegionalAgentWithdrawDO getRegionalAgentWithdraw(Long id);

    /**
     * 获得地区代理提现分页
     *
     * @param pageReqVO 分页查询
     * @return 地区代理提现分页
     */
    PageResult<RegionalAgentWithdrawDO> getRegionalAgentWithdrawPage(RegionalAgentWithdrawPageReqVO pageReqVO);

    /**
     * 申请提现
     *
     * @param userId      用户编号
     * @param createReqVO 创建信息
     * @return 提现编号
     */
    Long createRegionalAgentWithdraw(Long userId, @Valid AppRegionalAgentWithdrawCreateReqVO createReqVO);

    /**
     * 审核提现申请
     *
     * @param id          提现编号
     * @param status      审核状态
     * @param auditReason 审核原因
     */
    void auditRegionalAgentWithdraw(Long id, Integer status, String auditReason);

}