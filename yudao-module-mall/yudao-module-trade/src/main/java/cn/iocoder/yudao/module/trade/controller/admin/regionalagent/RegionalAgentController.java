package cn.iocoder.yudao.module.trade.controller.admin.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.agent.RegionalAgentPageReqVO;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.agent.RegionalAgentRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 地区代理
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 地区代理")
@RestController
@RequestMapping("/trade/regional-agent")
@Validated
public class RegionalAgentController {

    @GetMapping("/page")
    @Operation(summary = "获得地区代理分页")
    public CommonResult<PageResult<RegionalAgentRespVO>> getRegionalAgentPage(@Valid RegionalAgentPageReqVO pageReqVO) {
        // TODO: 实现地区代理分页查询逻辑
        return success(new PageResult<>());
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理")
    public CommonResult<RegionalAgentRespVO> getRegionalAgent(@RequestParam("id") Long id) {
        // TODO: 实现获取地区代理详情逻辑
        return success(new RegionalAgentRespVO());
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新地区代理状态")
    public CommonResult<Boolean> updateRegionalAgentStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        // TODO: 实现更新地区代理状态逻辑
        return success(true);
    }

    @PutMapping("/audit")
    @Operation(summary = "审核地区代理")
    public CommonResult<Boolean> auditRegionalAgent(@RequestParam("id") Long id, @RequestParam("status") Integer status, @RequestParam(value = "reason", required = false) String reason) {
        // TODO: 实现审核地区代理逻辑
        return success(true);
    }

}