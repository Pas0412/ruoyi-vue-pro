package cn.iocoder.yudao.module.product.convert.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.controller.app.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Mapping(target = "auditReason", ignore = true)
    @Mapping(target = "brokeragePrice", ignore = true)
    @Mapping(target = "frozenBrokeragePrice", ignore = true)
    @Mapping(target = "agentTime", ignore = true)
    RegionalAgentDO convert(AppRegionalAgentCreateReqVO bean, Long userId);

    @Mapping(target = "userId", source = "userId")
    RegionalAgentPageReqVO convert(AppRegionalAgentPageReqVO bean, Long userId);

    AppRegionalAgentRespVO convertApp(RegionalAgentDO bean);

    List<AppRegionalAgentRespVO> convertAppList(List<RegionalAgentDO> list);

    PageResult<AppRegionalAgentRespVO> convertAppPage(PageResult<RegionalAgentDO> page);

    /**
     * 将元转换为分
     * @param yuan 元金额（BigDecimal）
     * @return 分金额（Integer）
     */
    default Integer convertYuanToFen(BigDecimal yuan) {
        if (yuan == null) {
            return 0;
        }
        // 使用 setScale 确保精度，然后转换为分
        return yuan.setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).intValue();
    }

}