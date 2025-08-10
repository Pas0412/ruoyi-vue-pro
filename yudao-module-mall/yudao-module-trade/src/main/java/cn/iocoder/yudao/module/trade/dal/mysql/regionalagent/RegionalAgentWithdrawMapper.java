package cn.iocoder.yudao.module.trade.dal.mysql.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.withdraw.RegionalAgentWithdrawPageReqVO;
import cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.withdraw.AppRegionalAgentWithdrawPageReqVO;
import cn.iocoder.yudao.module.trade.dal.dataobject.regionalagent.RegionalAgentWithdrawDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 地区代理提现 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RegionalAgentWithdrawMapper extends BaseMapperX<RegionalAgentWithdrawDO> {

    default PageResult<RegionalAgentWithdrawDO> selectPage(RegionalAgentWithdrawPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RegionalAgentWithdrawDO>()
                .eqIfPresent(RegionalAgentWithdrawDO::getAgentId, reqVO.getAgentId())
                .eqIfPresent(RegionalAgentWithdrawDO::getUserId, reqVO.getUserId())
                .eqIfPresent(RegionalAgentWithdrawDO::getType, reqVO.getType())
                .eqIfPresent(RegionalAgentWithdrawDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RegionalAgentWithdrawDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegionalAgentWithdrawDO::getId));
    }

    default PageResult<RegionalAgentWithdrawDO> selectPage(AppRegionalAgentWithdrawPageReqVO reqVO, Long userId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RegionalAgentWithdrawDO>()
                .eq(RegionalAgentWithdrawDO::getUserId, userId)
                .eqIfPresent(RegionalAgentWithdrawDO::getType, reqVO.getType())
                .eqIfPresent(RegionalAgentWithdrawDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RegionalAgentWithdrawDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegionalAgentWithdrawDO::getId));
    }

    default int updateByIdAndStatus(Long id, Integer status, RegionalAgentWithdrawDO updateObj) {
        return update(updateObj, new LambdaQueryWrapper<RegionalAgentWithdrawDO>()
                .eq(RegionalAgentWithdrawDO::getId, id)
                .eq(RegionalAgentWithdrawDO::getStatus, status));
    }

    default List<RegionalAgentWithdrawDO> selectListByStatus(Integer status) {
        return selectList(RegionalAgentWithdrawDO::getStatus, status);
    }

    /**
     * 获取用户提现金额统计
     *
     * @param userId 用户编号
     * @param status 状态
     * @return 提现金额
     */
    @Select("SELECT IFNULL(SUM(price), 0) FROM trade_regional_agent_withdraw " +
            "WHERE user_id = #{userId} AND status = #{status} AND deleted = FALSE")
    Integer selectWithdrawPriceSummaryByUserIdAndStatus(@Param("userId") Long userId,
                                                         @Param("status") Integer status);

    /**
     * 获取指定状态的提现记录数量
     *
     * @param status 状态
     * @return 记录数量
     */
    @Select("SELECT COUNT(1) FROM trade_regional_agent_withdraw " +
            "WHERE status = #{status} AND deleted = FALSE")
    Long selectCountByStatus(@Param("status") Integer status);

}