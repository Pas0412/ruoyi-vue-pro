package cn.iocoder.yudao.module.product.controller.admin.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo.RegionalAgentRecordRespVO;
import cn.iocoder.yudao.module.product.convert.regionalagent.RegionalAgentRecordConvert;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import cn.iocoder.yudao.module.product.service.regionalagent.RegionalAgentRecordService;
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
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 地区代理记录")
@RestController
@RequestMapping("/product/regional-agent-record")
@Validated
public class RegionalAgentRecordController {

    @Resource
    private RegionalAgentRecordService regionalAgentRecordService;

    @GetMapping("/get")
    @Operation(summary = "获得地区代理记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:regional-agent-record:query')")
    public CommonResult<RegionalAgentRecordRespVO> getRegionalAgentRecord(@RequestParam("id") Long id) {
        RegionalAgentRecordDO regionalAgentRecord = regionalAgentRecordService.getRegionalAgentRecord(id);
        return success(RegionalAgentRecordConvert.INSTANCE.convert(regionalAgentRecord));
    }

    @GetMapping("/page")
    @Operation(summary = "获得地区代理记录分页")
    @PreAuthorize("@ss.hasPermission('product:regional-agent-record:query')")
    public CommonResult<PageResult<RegionalAgentRecordRespVO>> getRegionalAgentRecordPage(@Valid RegionalAgentRecordPageReqVO pageVO) {
        PageResult<RegionalAgentRecordDO> pageResult = regionalAgentRecordService.getRegionalAgentRecordPage(pageVO);
        return success(RegionalAgentRecordConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出地区代理记录 Excel")
    @PreAuthorize("@ss.hasPermission('product:regional-agent-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRegionalAgentRecordExcel(@Valid RegionalAgentRecordPageReqVO pageVO,
              HttpServletResponse response) throws IOException {
        pageVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RegionalAgentRecordDO> list = regionalAgentRecordService.getRegionalAgentRecordPage(pageVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "地区代理记录.xls", "数据", RegionalAgentRecordRespVO.class,
                        RegionalAgentRecordConvert.INSTANCE.convertList(list));
    }

}