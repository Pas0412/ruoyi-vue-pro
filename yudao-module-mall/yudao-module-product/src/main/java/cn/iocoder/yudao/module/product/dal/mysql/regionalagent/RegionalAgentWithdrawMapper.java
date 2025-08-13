package cn.iocoder.yudao.module.product.dal.mysql.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentWithdrawPageReqVO;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentWithdrawDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 地区代理提现 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RegionalAgentWithdrawMapper extends BaseMapperX<RegionalAgentWithdrawDO> {

    default PageResult<RegionalAgentWithdrawDO> selectPage(RegionalAgentWithdrawPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RegionalAgentWithdrawDO>()
                .eqIfPresent(RegionalAgentWithdrawDO::getUserId, reqVO.getUserId())
                .eqIfPresent(RegionalAgentWithdrawDO::getType, reqVO.getType())
                .eqIfPresent(RegionalAgentWithdrawDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RegionalAgentWithdrawDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RegionalAgentWithdrawDO::getId));
    }

    default int updateByIdAndStatus(Long id, Integer whereStatus, RegionalAgentWithdrawDO updateObj) {
        return update(updateObj, new LambdaUpdateWrapper<RegionalAgentWithdrawDO>()
                .eq(RegionalAgentWithdrawDO::getId, id)
                .eq(RegionalAgentWithdrawDO::getStatus, whereStatus));
    }

}