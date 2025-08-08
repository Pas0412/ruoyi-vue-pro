package cn.iocoder.yudao.module.trade.controller.app.areaagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.record.*;
import cn.iocoder.yudao.module.trade.convert.areaagent.TradeAreaAgentRecordConvert;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentRecordDO;
import cn.iocoder.yudao.module.trade.service.areaagent.TradeAreaAgentRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 地区代理佣金记录")
@RestController
@RequestMapping("/trade/area-agent-record")
@Validated
@Slf4j
public class AppTradeAreaAgentRecordController {

    @Resource
    private TradeAreaAgentRecordService tradeAreaAgentRecordService;

    @GetMapping("/page")
    @Operation(summary = "获得个人地区代理佣金记录分页")
    public CommonResult<PageResult<AppTradeAreaAgentRecordRespVO>> getAreaAgentRecordPage(
            @Valid AppTradeAreaAgentRecordPageReqVO pageReqVO) {
        pageReqVO.setAgentUserId(getLoginUserId());
        PageResult<TradeAreaAgentRecordDO> pageResult = tradeAreaAgentRecordService.getTradeAreaAgentRecordPage(pageReqVO);
        return success(TradeAreaAgentRecordConvert.INSTANCE.convertAppPage(pageResult));
    }

    @GetMapping("/get-summary")
    @Operation(summary = "获得个人地区代理佣金汇总")
    public CommonResult<AppTradeAreaAgentRecordSummaryRespVO> getAreaAgentRecordSummary() {
        return success(tradeAreaAgentRecordService.getAreaAgentRecordSummary(getLoginUserId()));
    }

    @GetMapping("/get-monthly-summary")
    @Operation(summary = "获得个人地区代理月度佣金汇总")
    public CommonResult<AppTradeAreaAgentRecordMonthlySummaryRespVO> getAreaAgentRecordMonthlySummary(
            @RequestParam("year") Integer year) {
        return success(tradeAreaAgentRecordService.getAreaAgentRecordMonthlySummary(getLoginUserId(), year));
    }

    @GetMapping("/get-trend")
    @Operation(summary = "获得个人地区代理佣金趋势")
    public CommonResult<AppTradeAreaAgentRecordTrendRespVO> getAreaAgentRecordTrend(
            @RequestParam("days") Integer days) {
        return success(tradeAreaAgentRecordService.getAreaAgentRecordTrend(getLoginUserId(), days));
    }

}