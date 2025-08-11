package cn.iocoder.yudao.module.product.convert.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.controller.app.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentWithdrawDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 地区代理提现 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface RegionalAgentWithdrawConvert {

    RegionalAgentWithdrawConvert INSTANCE = Mappers.getMapper(RegionalAgentWithdrawConvert.class);

    RegionalAgentWithdrawRespVO convert(RegionalAgentWithdrawDO bean);

    List<RegionalAgentWithdrawRespVO> convertList(List<RegionalAgentWithdrawDO> list);

    PageResult<RegionalAgentWithdrawRespVO> convertPage(PageResult<RegionalAgentWithdrawDO> page);

    // ========== 用户端相关 ==========

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "feePrice", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "auditReason", ignore = true)
    @Mapping(target = "auditTime", ignore = true)
    @Mapping(target = "auditUserId", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    RegionalAgentWithdrawDO convert(AppRegionalAgentWithdrawCreateReqVO bean, Long userId);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "feePrice", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "accountNo", ignore = true)
    @Mapping(target = "accountQrCodeUrl", ignore = true)
    @Mapping(target = "bankName", ignore = true)
    @Mapping(target = "bankAddress", ignore = true)
    @Mapping(target = "auditReason", ignore = true)
    @Mapping(target = "auditTime", ignore = true)
    @Mapping(target = "auditUserId", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    RegionalAgentWithdrawPageReqVO convert(AppRegionalAgentWithdrawPageReqVO bean, Long userId);

    AppRegionalAgentWithdrawRespVO convertApp(RegionalAgentWithdrawDO bean);

    List<AppRegionalAgentWithdrawRespVO> convertAppList(List<RegionalAgentWithdrawDO> list);

    PageResult<AppRegionalAgentWithdrawRespVO> convertAppPage(PageResult<RegionalAgentWithdrawDO> page);

}