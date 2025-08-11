package cn.iocoder.yudao.module.product.convert.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.controller.app.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 地区代理记录 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface RegionalAgentRecordConvert {

    RegionalAgentRecordConvert INSTANCE = Mappers.getMapper(RegionalAgentRecordConvert.class);

    RegionalAgentRecordRespVO convert(RegionalAgentRecordDO bean);

    List<RegionalAgentRecordRespVO> convertList(List<RegionalAgentRecordDO> list);

    PageResult<RegionalAgentRecordRespVO> convertPage(PageResult<RegionalAgentRecordDO> page);

    // ========== 用户端相关 ==========

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "frozenTime", ignore = true)
    @Mapping(target = "unfreezeTime", ignore = true)
    @Mapping(target = "sourceUserLevel", ignore = true)
    @Mapping(target = "sourceUserId", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    RegionalAgentRecordPageReqVO convert(AppRegionalAgentRecordPageReqVO bean, Long userId);

    AppRegionalAgentRecordRespVO convertApp(RegionalAgentRecordDO bean);

    List<AppRegionalAgentRecordRespVO> convertAppList(List<RegionalAgentRecordDO> list);

    PageResult<AppRegionalAgentRecordRespVO> convertAppPage(PageResult<RegionalAgentRecordDO> page);

}