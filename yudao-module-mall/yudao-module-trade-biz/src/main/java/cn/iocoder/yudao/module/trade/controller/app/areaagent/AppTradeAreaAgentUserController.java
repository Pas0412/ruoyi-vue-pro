package cn.iocoder.yudao.module.trade.controller.app.areaagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.app.areaagent.vo.user.*;
import cn.iocoder.yudao.module.trade.convert.areaagent.TradeAreaAgentUserConvert;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentUserDO;
import cn.iocoder.yudao.module.trade.service.areaagent.TradeAreaAgentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 地区代理用户")
@RestController
@RequestMapping("/trade/area-agent-user")
@Validated
@Slf4j
public class AppTradeAreaAgentUserController {

    @Resource
    private TradeAreaAgentUserService tradeAreaAgentUserService;

    @GetMapping("/get")
    @Operation(summary = "获得个人地区代理信息")
    public CommonResult<AppTradeAreaAgentUserRespVO> getAreaAgentUser() {
        TradeAreaAgentUserDO areaAgentUser = tradeAreaAgentUserService.getTradeAreaAgentUserByUserId(getLoginUserId());
        return success(TradeAreaAgentUserConvert.INSTANCE.convertApp(areaAgentUser));
    }

    @PostMapping("/apply")
    @Operation(summary = "申请成为地区代理")
    public CommonResult<Boolean> applyAreaAgent(@Valid @RequestBody AppTradeAreaAgentUserApplyReqVO applyReqVO) {
        tradeAreaAgentUserService.applyAreaAgent(getLoginUserId(), applyReqVO.getAreaId());
        return success(true);
    }

    @GetMapping("/get-summary")
    @Operation(summary = "获得个人地区代理统计")
    public CommonResult<AppTradeAreaAgentUserSummaryRespVO> getAreaAgentUserSummary() {
        return success(tradeAreaAgentUserService.getAreaAgentUserSummary(getLoginUserId()));
    }

    @GetMapping("/get-child-page")
    @Operation(summary = "获得下级代理分页")
    public CommonResult<PageResult<AppTradeAreaAgentUserChildRespVO>> getAreaAgentUserChildPage(
            @Valid AppTradeAreaAgentUserChildPageReqVO pageReqVO) {
        pageReqVO.setUserId(getLoginUserId());
        return success(tradeAreaAgentUserService.getAreaAgentUserChildPage(pageReqVO));
    }

    @GetMapping("/get-area-tree")
    @Operation(summary = "获得可申请的地区树")
    public CommonResult<AppTradeAreaAgentUserAreaTreeRespVO> getAreaAgentUserAreaTree() {
        return success(tradeAreaAgentUserService.getAreaAgentUserAreaTree(getLoginUserId()));
    }

    @GetMapping("/check-area-available")
    @Operation(summary = "检查地区是否可申请")
    @Parameter(name = "areaId", description = "地区编号", required = true, example = "110000")
    public CommonResult<Boolean> checkAreaAvailable(@RequestParam("areaId") Long areaId) {
        return success(tradeAreaAgentUserService.checkAreaAvailable(areaId));
    }

}