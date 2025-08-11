package cn.iocoder.yudao.module.product.controller.admin.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.convert.regionalagent.RegionalAgentWithdrawConvert;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentWithdrawDO;
import cn.iocoder.yudao.module.product.service.regionalagent.RegionalAgentWithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 地区代理提现")
@RestController
@RequestMapping("/product/regional-agent-withdraw")
@Validated
public class RegionalAgentWithdrawController {

    @Resource
    private RegionalAgentWithdrawService regionalAgentWithdrawService;

    @PostMapping("/create")
    @Operation(summary = "创建地区代理提现")
    @PreAuthorize("@ss.hasPermission('product:regional-agent-withdraw:create')")
    public CommonResult<Long> createRegionalAgentWithdraw(@Valid @RequestBody RegionalAgentWithdrawCreateReqVO createReqVO) {
        return success(regionalAgentWithdrawService.createRegionalAgentWithdraw(getLoginUserId(), createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理提现")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:regional-agent-withdraw:query')")
    public CommonResult<RegionalAgentWithdrawRespVO> getRegionalAgentWithdraw(@RequestParam("id") Long id) {
        RegionalAgentWithdrawDO regionalAgentWithdraw = regionalAgentWithdrawService.getRegionalAgentWithdraw(id);
        return success(RegionalAgentWithdrawConvert.INSTANCE.convert(regionalAgentWithdraw));
    }

    @GetMapping("/page")
    @Operation(summary = "获得地区代理提现分页")
    @PreAuthorize("@ss.hasPermission('product:regional-agent-withdraw:query')")
    public CommonResult<PageResult<RegionalAgentWithdrawRespVO>> getRegionalAgentWithdrawPage(@Valid RegionalAgentWithdrawPageReqVO pageVO) {
        PageResult<RegionalAgentWithdrawDO> pageResult = regionalAgentWithdrawService.getRegionalAgentWithdrawPage(pageVO);
        return success(RegionalAgentWithdrawConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出地区代理提现 Excel")
    @PreAuthorize("@ss.hasPermission('product:regional-agent-withdraw:export')")
    @OperateLog(type = EXPORT)
    public void exportRegionalAgentWithdrawExcel(@Valid RegionalAgentWithdrawPageReqVO pageVO,
              HttpServletResponse response) throws IOException {
        pageVO.setPageSize(PageResult.PAGE_SIZE_NONE);
        List<RegionalAgentWithdrawDO> list = regionalAgentWithdrawService.getRegionalAgentWithdrawPage(pageVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "地区代理提现.xls", "数据", RegionalAgentWithdrawRespVO.class,
                        RegionalAgentWithdrawConvert.INSTANCE.convertList(list));
    }

    @PutMapping("/approve")
    @Operation(summary = "审核地区代理提现")
    @PreAuthorize("@ss.hasPermission('product:regional-agent-withdraw:approve')")
    public CommonResult<Boolean> approveRegionalAgentWithdraw(@Valid @RequestBody RegionalAgentWithdrawApproveReqVO approveReqVO) {
        regionalAgentWithdrawService.approveRegionalAgentWithdraw(approveReqVO, getLoginUserId());
        return success(true);
    }

}