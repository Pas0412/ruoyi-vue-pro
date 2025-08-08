package cn.iocoder.yudao.module.trade.controller.admin.areaagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user.*;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentUserDO;
import cn.iocoder.yudao.module.trade.service.areaagent.TradeAreaAgentUserService;
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

@Tag(name = "管理后台 - 地区代理用户")
@RestController
@RequestMapping("/trade/area-agent-user")
@Validated
public class TradeAreaAgentUserController {

    @Resource
    private TradeAreaAgentUserService areaAgentUserService;

    @PostMapping("/create")
    @Operation(summary = "创建地区代理用户")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-user:create')")
    public CommonResult<Long> createAreaAgentUser(@Valid @RequestBody TradeAreaAgentUserCreateReqVO createReqVO) {
        return success(areaAgentUserService.createAreaAgentUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新地区代理用户")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-user:update')")
    public CommonResult<Boolean> updateAreaAgentUser(@Valid @RequestBody TradeAreaAgentUserUpdateReqVO updateReqVO) {
        areaAgentUserService.updateAreaAgentUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除地区代理用户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('trade:area-agent-user:delete')")
    public CommonResult<Boolean> deleteAreaAgentUser(@RequestParam("id") Long id) {
        areaAgentUserService.deleteAreaAgentUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理用户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-user:query')")
    public CommonResult<TradeAreaAgentUserRespVO> getAreaAgentUser(@RequestParam("id") Long id) {
        TradeAreaAgentUserDO areaAgentUser = areaAgentUserService.getAreaAgentUser(id);
        return success(BeanUtils.toBean(areaAgentUser, TradeAreaAgentUserRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得地区代理用户分页")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-user:query')")
    public CommonResult<PageResult<TradeAreaAgentUserRespVO>> getAreaAgentUserPage(@Valid TradeAreaAgentUserPageReqVO pageReqVO) {
        PageResult<TradeAreaAgentUserDO> pageResult = areaAgentUserService.getAreaAgentUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TradeAreaAgentUserRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出地区代理用户 Excel")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-user:export')")
    @OperateLog(type = EXPORT)
    public void exportAreaAgentUserExcel(@Valid TradeAreaAgentUserPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TradeAreaAgentUserDO> list = areaAgentUserService.getAreaAgentUserPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "地区代理用户.xls", "数据", TradeAreaAgentUserRespVO.class,
                        BeanUtils.toBean(list, TradeAreaAgentUserRespVO.class));
    }

    @PutMapping("/update-enabled")
    @Operation(summary = "启用/禁用地区代理用户")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-user:update')")
    public CommonResult<Boolean> updateAreaAgentUserEnabled(@RequestParam("id") Long id,
                                                           @RequestParam("enabled") Boolean enabled) {
        areaAgentUserService.updateAreaAgentUserEnabled(id, enabled);
        return success(true);
    }

}