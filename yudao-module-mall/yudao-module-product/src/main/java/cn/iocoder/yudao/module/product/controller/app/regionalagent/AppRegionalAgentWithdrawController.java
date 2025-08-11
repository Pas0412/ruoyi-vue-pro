package cn.iocoder.yudao.module.product.controller.app.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.product.controller.app.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.convert.regionalagent.RegionalAgentWithdrawConvert;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentWithdrawDO;
import cn.iocoder.yudao.module.product.service.regionalagent.RegionalAgentWithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

/**
 * 用户 APP - 地区代理提现 RESTful API
 *
 * @author 芋道源码
 */
@Tag(name = "用户 APP - 地区代理提现")
@RestController
@RequestMapping("/product/regional-agent-withdraw")
@Validated
@Slf4j
public class AppRegionalAgentWithdrawController {

    @Resource
    private RegionalAgentWithdrawService regionalAgentWithdrawService;

    @PostMapping("/create")
    @Operation(summary = "申请提现")
    @PermitAll
    public CommonResult<Long> createRegionalAgentWithdraw(@Valid @RequestBody AppRegionalAgentWithdrawCreateReqVO createReqVO) {
        return success(regionalAgentWithdrawService.createRegionalAgentWithdraw(getLoginUserId(), createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理提现")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PermitAll
    public CommonResult<AppRegionalAgentWithdrawRespVO> getRegionalAgentWithdraw(@RequestParam("id") Long id) {
        RegionalAgentWithdrawDO regionalAgentWithdraw = regionalAgentWithdrawService.getRegionalAgentWithdraw(id);
        return success(RegionalAgentWithdrawConvert.INSTANCE.convertApp(regionalAgentWithdraw));
    }

    @GetMapping("/page")
    @Operation(summary = "获得地区代理提现分页")
    @PermitAll
    public CommonResult<PageResult<AppRegionalAgentWithdrawRespVO>> getRegionalAgentWithdrawPage(@Valid AppRegionalAgentWithdrawPageReqVO pageVO) {
        PageResult<RegionalAgentWithdrawDO> pageResult = regionalAgentWithdrawService.getRegionalAgentWithdrawPage(RegionalAgentWithdrawConvert.INSTANCE.convert(pageVO, getLoginUserId()));
        return success(RegionalAgentWithdrawConvert.INSTANCE.convertAppPage(pageResult));
    }

}