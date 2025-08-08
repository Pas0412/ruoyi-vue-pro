package cn.iocoder.yudao.module.trade.controller.admin.areaagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.config.*;
import cn.iocoder.yudao.module.trade.dal.dataobject.areaagent.TradeAreaAgentConfigDO;
import cn.iocoder.yudao.module.trade.service.areaagent.TradeAreaAgentConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 地区代理配置")
@RestController
@RequestMapping("/trade/area-agent-config")
@Validated
public class TradeAreaAgentConfigController {

    @Resource
    private TradeAreaAgentConfigService areaAgentConfigService;

    @PutMapping("/update")
    @Operation(summary = "更新地区代理配置")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-config:update')")
    public CommonResult<Boolean> updateAreaAgentConfig(@Valid @RequestBody TradeAreaAgentConfigUpdateReqVO updateReqVO) {
        areaAgentConfigService.updateAreaAgentConfig(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-config:query')")
    public CommonResult<TradeAreaAgentConfigRespVO> getAreaAgentConfig(@RequestParam("id") Long id) {
        TradeAreaAgentConfigDO areaAgentConfig = areaAgentConfigService.getAreaAgentConfig(id);
        return success(BeanUtils.toBean(areaAgentConfig, TradeAreaAgentConfigRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得地区代理配置列表")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-config:query')")
    public CommonResult<List<TradeAreaAgentConfigRespVO>> getAreaAgentConfigList() {
        List<TradeAreaAgentConfigDO> list = areaAgentConfigService.getAreaAgentConfigList();
        return success(BeanUtils.toBean(list, TradeAreaAgentConfigRespVO.class));
    }

    @GetMapping("/get-by-level")
    @Operation(summary = "根据代理等级获得地区代理配置")
    @Parameter(name = "areaLevel", description = "代理等级", required = true)
    @PreAuthorize("@ss.hasPermission('trade:area-agent-config:query')")
    public CommonResult<TradeAreaAgentConfigRespVO> getAreaAgentConfigByLevel(@RequestParam("areaLevel") Integer areaLevel) {
        TradeAreaAgentConfigDO areaAgentConfig = areaAgentConfigService.getAreaAgentConfigByLevel(areaLevel);
        return success(BeanUtils.toBean(areaAgentConfig, TradeAreaAgentConfigRespVO.class));
    }

    @GetMapping("/enabled-list")
    @Operation(summary = "获得所有启用的地区代理配置")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-config:query')")
    public CommonResult<List<TradeAreaAgentConfigRespVO>> getEnabledAreaAgentConfigList() {
        List<TradeAreaAgentConfigDO> list = areaAgentConfigService.getEnabledAreaAgentConfigList();
        return success(BeanUtils.toBean(list, TradeAreaAgentConfigRespVO.class));
    }

    @PutMapping("/update-enabled")
    @Operation(summary = "启用/禁用地区代理配置")
    @PreAuthorize("@ss.hasPermission('trade:area-agent-config:update')")
    public CommonResult<Boolean> updateAreaAgentConfigEnabled(@RequestParam("id") Long id,
                                                             @RequestParam("enabled") Boolean enabled) {
        areaAgentConfigService.updateAreaAgentConfigEnabled(id, enabled);
        return success(true);
    }

}