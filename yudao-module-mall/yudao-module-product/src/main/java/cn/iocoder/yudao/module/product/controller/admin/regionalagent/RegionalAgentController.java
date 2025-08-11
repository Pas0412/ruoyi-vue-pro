package cn.iocoder.yudao.module.product.controller.admin.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.convert.regionalagent.RegionalAgentConvert;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentDO;
import cn.iocoder.yudao.module.product.service.regionalagent.RegionalAgentService;
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
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 地区代理")
@RestController
@RequestMapping("/product/regional-agent")
@Validated
public class RegionalAgentController {

    @Resource
    private RegionalAgentService regionalAgentService;

    @PostMapping("/create")
    @Operation(summary = "创建地区代理")
    @PreAuthorize("@ss.hasPermission('product:regional-agent:create')")
    public CommonResult<Long> createRegionalAgent(@Valid @RequestBody RegionalAgentCreateReqVO createReqVO) {
        return success(regionalAgentService.createRegionalAgent(RegionalAgentConvert.INSTANCE.convert(createReqVO)));
    }

    @PutMapping("/update")
    @Operation(summary = "更新地区代理")
    @PreAuthorize("@ss.hasPermission('product:regional-agent:update')")
    public CommonResult<Boolean> updateRegionalAgent(@Valid @RequestBody RegionalAgentUpdateReqVO updateReqVO) {
        regionalAgentService.updateRegionalAgent(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除地区代理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('product:regional-agent:delete')")
    public CommonResult<Boolean> deleteRegionalAgent(@RequestParam("id") Long id) {
        regionalAgentService.deleteRegionalAgent(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:regional-agent:query')")
    public CommonResult<RegionalAgentRespVO> getRegionalAgent(@RequestParam("id") Long id) {
        RegionalAgentDO regionalAgent = regionalAgentService.getRegionalAgent(id);
        return success(RegionalAgentConvert.INSTANCE.convert(regionalAgent));
    }

    @GetMapping("/list")
    @Operation(summary = "获得地区代理列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('product:regional-agent:query')")
    public CommonResult<List<RegionalAgentRespVO>> getRegionalAgentList(@RequestParam("ids") Collection<Long> ids) {
        List<RegionalAgentDO> list = regionalAgentService.getRegionalAgentList(ids);
        return success(RegionalAgentConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得地区代理分页")
    @PreAuthorize("@ss.hasPermission('product:regional-agent:query')")
    public CommonResult<PageResult<RegionalAgentRespVO>> getRegionalAgentPage(@Valid RegionalAgentPageReqVO pageVO) {
        PageResult<RegionalAgentDO> pageResult = regionalAgentService.getRegionalAgentPage(pageVO);
        return success(RegionalAgentConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出地区代理 Excel")
    @PreAuthorize("@ss.hasPermission('product:regional-agent:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRegionalAgentExcel(@Valid RegionalAgentPageReqVO pageVO,
              HttpServletResponse response) throws IOException {
        pageVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RegionalAgentDO> list = regionalAgentService.getRegionalAgentPage(pageVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "地区代理.xls", "数据", RegionalAgentRespVO.class,
                        RegionalAgentConvert.INSTANCE.convertList(list));
    }

    @PutMapping("/approve")
    @Operation(summary = "审核地区代理")
    @PreAuthorize("@ss.hasPermission('product:regional-agent:approve')")
    public CommonResult<Boolean> approveRegionalAgent(@Valid @RequestBody RegionalAgentApproveReqVO approveReqVO) {
        regionalAgentService.auditRegionalAgent(approveReqVO.getId(), approveReqVO.getStatus(), approveReqVO.getAuditRemark());
        return success(true);
    }

}