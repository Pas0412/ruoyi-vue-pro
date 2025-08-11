package cn.iocoder.yudao.module.product.controller.app.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import javax.annotation.security.PermitAll;
import java.util.List;
import cn.iocoder.yudao.module.product.controller.app.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.convert.regionalagent.RegionalAgentConvert;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentDO;
import cn.iocoder.yudao.module.product.service.regionalagent.RegionalAgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

/**
 * 用户 APP - 地区代理 RESTful API
 *
 * @author 芋道源码
 */
@Tag(name = "用户 APP - 地区代理")
@RestController
@RequestMapping("/product/regional-agent")
@Validated
@Slf4j
public class AppRegionalAgentController {

    @Resource
    private RegionalAgentService regionalAgentService;

    @PostMapping("/create")
    @Operation(summary = "申请成为地区代理")
    @PermitAll
    public CommonResult<Long> createRegionalAgent(@Valid @RequestBody AppRegionalAgentCreateReqVO createReqVO) {
        return success(regionalAgentService.createRegionalAgent(RegionalAgentConvert.INSTANCE.convert(createReqVO, getLoginUserId())));
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PermitAll
    public CommonResult<AppRegionalAgentRespVO> getRegionalAgent(@RequestParam("id") Long id) {
        RegionalAgentDO regionalAgent = regionalAgentService.getRegionalAgent(id);
        return success(RegionalAgentConvert.INSTANCE.convertApp(regionalAgent));
    }

    @GetMapping("/page")
    @Operation(summary = "获得地区代理分页")
    @PermitAll
    public CommonResult<PageResult<AppRegionalAgentRespVO>> getRegionalAgentPage(@Valid AppRegionalAgentPageReqVO pageVO) {
        PageResult<RegionalAgentDO> pageResult = regionalAgentService.getRegionalAgentPage(RegionalAgentConvert.INSTANCE.convert(pageVO, getLoginUserId()));
        return success(RegionalAgentConvert.INSTANCE.convertAppPage(pageResult));
    }

    @GetMapping("/get-by-user")
    @Operation(summary = "根据用户获得地区代理")
    @PermitAll
    public CommonResult<List<AppRegionalAgentRespVO>> getRegionalAgentByUserId() {
        List<RegionalAgentDO> regionalAgents = regionalAgentService.getApprovedRegionalAgentsByUserId(getLoginUserId());
        return success(RegionalAgentConvert.INSTANCE.convertAppList(regionalAgents));
    }

}