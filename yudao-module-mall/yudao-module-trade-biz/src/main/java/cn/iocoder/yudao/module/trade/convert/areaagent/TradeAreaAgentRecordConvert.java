package cn.iocoder.yudao.module.trade.convert.areaagent;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.record.*;
import cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.record.*;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentRecordDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 地区代理佣金记录 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface TradeAreaAgentRecordConvert {

    TradeAreaAgentRecordConvert INSTANCE = Mappers.getMapper(TradeAreaAgentRecordConvert.class);

    TradeAreaAgentRecordRespVO convert(TradeAreaAgentRecordDO bean);

    List<TradeAreaAgentRecordRespVO> convertList(List<TradeAreaAgentRecordDO> list);

    PageResult<TradeAreaAgentRecordRespVO> convertPage(PageResult<TradeAreaAgentRecordDO> page);

    // ========== App 相关 ==========

    AppTradeAreaAgentRecordRespVO convertApp(TradeAreaAgentRecordDO bean);

    List<AppTradeAreaAgentRecordRespVO> convertAppList(List<TradeAreaAgentRecordDO> list);

    PageResult<AppTradeAreaAgentRecordRespVO> convertAppPage(PageResult<TradeAreaAgentRecordDO> page);

}