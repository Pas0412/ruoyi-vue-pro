package cn.iocoder.yudao.module.trade.controller.admin.areaagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.record.*;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentRecordDO;
import cn.iocoder.yudao.module.trade.service.areaagent.TradeAreaAgentRecordService;
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
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 地区代理佣金记录")
@RestController
@RequestMapping("/trade/area-agent-record")
@Validated
public class TradeAreaAgentRecordController {

    @Resource
    private TradeAreaAgentRecordService areaAgentRecordService;

    @GetMapping("/get")
    @Operation(summary = "获得地区代理佣金记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-record:query')")
    public CommonResult<TradeAreaAgentRecordRespVO> getAreaAgentRecord(@RequestParam("id") Long id) {
        TradeAreaAgentRecordDO areaAgentRecord = areaAgentRecordService.getAreaAgentRecord(id);
        return success(BeanUtils.toBean(areaAgentRecord, TradeAreaAgentRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得地区代理佣金记录分页")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-record:query')")
    public CommonResult<PageResult<TradeAreaAgentRecordRespVO>> getAreaAgentRecordPage(@Valid TradeAreaAgentRecordPageReqVO pageReqVO) {
        PageResult<TradeAreaAgentRecordDO> pageResult = areaAgentRecordService.getAreaAgentRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TradeAreaAgentRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出地区代理佣金记录 Excel")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-record:export')")
    @OperateLog(type = EXPORT)
    public void exportAreaAgentRecordExcel(@Valid TradeAreaAgentRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TradeAreaAgentRecordDO> list = areaAgentRecordService.getAreaAgentRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "地区代理佣金记录.xls", "数据", TradeAreaAgentRecordRespVO.class,
                        BeanUtils.toBean(list, TradeAreaAgentRecordRespVO.class));
    }

    @GetMapping("/list-by-user-id")
    @Operation(summary = "根据用户编号获得地区代理佣金记录列表")
    @Parameter(name = "userId", description = "用户编号", required = true)
    @PreAuthorize("@ss.hasPermission('trade:area-agent-record:query')")
    public CommonResult<List<TradeAreaAgentRecordRespVO>> getAreaAgentRecordListByUserId(@RequestParam("userId") Long userId) {
        List<TradeAreaAgentRecordDO> list = areaAgentRecordService.getAreaAgentRecordListByUserId(userId);
        return success(BeanUtils.toBean(list, TradeAreaAgentRecordRespVO.class));
    }

    @GetMapping("/summary-price")
    @Operation(summary = "根据用户编号汇总佣金")
    @Parameter(name = "userId", description = "用户编号", required = true)
    @Parameter(name = "status", description = "状态", required = false)
    @PreAuthorize("@ss.hasPermission('trade:area-agent-record:query')")
    public CommonResult<Integer> getSummaryPriceByUserId(@RequestParam("userId") Long userId,
                                                        @RequestParam(value = "status", required = false) Integer status) {
        Integer summaryPrice = areaAgentRecordService.getSummaryPriceByUserId(userId, status);
        return success(summaryPrice);
    }

}