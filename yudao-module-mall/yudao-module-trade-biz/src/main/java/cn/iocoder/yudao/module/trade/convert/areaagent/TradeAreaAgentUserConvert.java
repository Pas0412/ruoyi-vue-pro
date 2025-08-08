package cn.iocoder.yudao.module.trade.convert.areaagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user.*;
import cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.user.*;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 地区代理用户 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface TradeAreaAgentUserConvert {

    TradeAreaAgentUserConvert INSTANCE = Mappers.getMapper(TradeAreaAgentUserConvert.class);

    TradeAreaAgentUserDO convert(TradeAreaAgentUserCreateReqVO bean);

    TradeAreaAgentUserDO convert(TradeAreaAgentUserUpdateReqVO bean);

    TradeAreaAgentUserRespVO convert(TradeAreaAgentUserDO bean);

    List<TradeAreaAgentUserRespVO> convertList(List<TradeAreaAgentUserDO> list);

    PageResult<TradeAreaAgentUserRespVO> convertPage(PageResult<TradeAreaAgentUserDO> page);

    // ========== App 相关 ==========

    AppTradeAreaAgentUserRespVO convertApp(TradeAreaAgentUserDO bean);

    List<AppTradeAreaAgentUserChildRespVO> convertAppChildList(List<TradeAreaAgentUserDO> list);

    PageResult<AppTradeAreaAgentUserChildRespVO> convertAppChildPage(PageResult<TradeAreaAgentUserDO> page);

}