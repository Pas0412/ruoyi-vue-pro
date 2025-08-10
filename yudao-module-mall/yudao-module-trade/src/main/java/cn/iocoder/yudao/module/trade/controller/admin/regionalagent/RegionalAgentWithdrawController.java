package cn.iocoder.yudao.module.trade.controller.admin.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.withdraw.RegionalAgentWithdrawPageReqVO;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.withdraw.RegionalAgentWithdrawRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 地区代理提现
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 地区代理提现")
@RestController
@RequestMapping("/trade/regional-agent/withdraw")
@Validated
public class RegionalAgentWithdrawController {

    @GetMapping("/page")
    @Operation(summary = "获得地区代理提现分页")
    public CommonResult<PageResult<RegionalAgentWithdrawRespVO>> getRegionalAgentWithdrawPage(@Valid RegionalAgentWithdrawPageReqVO pageReqVO) {
        // TODO: 实现地区代理提现分页查询逻辑
        return success(new PageResult<>());
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理提现")
    public CommonResult<RegionalAgentWithdrawRespVO> getRegionalAgentWithdraw(@RequestParam("id") Long id) {
        // TODO: 实现获取地区代理提现详情逻辑
        return success(new RegionalAgentWithdrawRespVO());
    }

    @PutMapping("/audit")
    @Operation(summary = "审核地区代理提现")
    public CommonResult<Boolean> auditRegionalAgentWithdraw(@RequestParam("id") Long id, @RequestParam("status") Integer status, @RequestParam(value = "reason", required = false) String reason) {
        // TODO: 实现审核地区代理提现逻辑
        return success(true);
    }

}