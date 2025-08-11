package cn.iocoder.yudao.module.product.convert.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.controller.app.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 地区代理 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface RegionalAgentConvert {

    RegionalAgentConvert INSTANCE = Mappers.getMapper(RegionalAgentConvert.class);

    RegionalAgentDO convert(RegionalAgentCreateReqVO bean);

    RegionalAgentDO convert(RegionalAgentUpdateReqVO bean);

    RegionalAgentRespVO convert(RegionalAgentDO bean);

    List<RegionalAgentRespVO> convertList(List<RegionalAgentDO> list);

    PageResult<RegionalAgentRespVO> convertPage(PageResult<RegionalAgentDO> page);

    // ========== 用户端相关 ==========

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "applyTime", ignore = true)
    @Mapping(target = "auditTime", ignore = true)
    @Mapping(target = "auditUserId", ignore = true)
    @Mapping(target = "auditRemark", ignore = true)
    @Mapping(target = "brokeragePrice", ignore = true)
    @Mapping(target = "frozenBrokeragePrice", ignore = true)
    @Mapping(target = "agentTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    RegionalAgentDO convert(AppRegionalAgentCreateReqVO bean, Long userId);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "applyTime", ignore = true)
    @Mapping(target = "auditTime", ignore = true)
    @Mapping(target = "auditUserId", ignore = true)
    @Mapping(target = "auditRemark", ignore = true)
    @Mapping(target = "brokeragePrice", ignore = true)
    @Mapping(target = "frozenBrokeragePrice", ignore = true)
    @Mapping(target = "agentTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    RegionalAgentPageReqVO convert(AppRegionalAgentPageReqVO bean, Long userId);

    AppRegionalAgentRespVO convertApp(RegionalAgentDO bean);

    List<AppRegionalAgentRespVO> convertAppList(List<RegionalAgentDO> list);

    PageResult<AppRegionalAgentRespVO> convertAppPage(PageResult<RegionalAgentDO> page);

}