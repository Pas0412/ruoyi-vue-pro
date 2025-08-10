package cn.iocoder.yudao.module.trade.controller.app.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.withdraw.AppRegionalAgentWithdrawPageReqVO;
import cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.withdraw.AppRegionalAgentWithdrawRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 用户 App - 地区代理提现
 *
 * @author 芋道源码
 */
@Tag(name = "用户 App - 地区代理提现")
@RestController
@RequestMapping("/trade/regional-agent/withdraw")
@Validated
public class AppRegionalAgentWithdrawController {

    @GetMapping("/page")
    @Operation(summary = "获得地区代理提现分页")
    public CommonResult<PageResult<AppRegionalAgentWithdrawRespVO>> getRegionalAgentWithdrawPage(@Valid AppRegionalAgentWithdrawPageReqVO pageReqVO) {
        // TODO: 实现地区代理提现分页查询逻辑
        return success(new PageResult<>());
    }

    @PostMapping("/create")
    @Operation(summary = "申请地区代理提现")
    public CommonResult<Long> createRegionalAgentWithdraw(@RequestParam("amount") Integer amount, @RequestParam("type") Integer type) {
        // TODO: 实现申请地区代理提现逻辑
        return success(1L);
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理提现")
    public CommonResult<AppRegionalAgentWithdrawRespVO> getRegionalAgentWithdraw(@RequestParam("id") Long id) {
        // TODO: 实现获取地区代理提现详情逻辑
        return success(new AppRegionalAgentWithdrawRespVO());
    }

}