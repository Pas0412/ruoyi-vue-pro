package cn.iocoder.yudao.module.system.controller.admin.area;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.area.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.area.SystemAreaDO;
import cn.iocoder.yudao.module.system.service.area.SystemAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 地区数据")
@RestController
@RequestMapping("/system/area")
@Validated
public class SystemAreaController {

    @Resource
    private SystemAreaService areaService;

    @GetMapping("/get")
    @Operation(summary = "获得地区数据")
    @Parameter(name = "id", description = "编号", required = true, example = "110000")
    @PreAuthorize("@ss.hasPermission('system:area:query')")
    public CommonResult<SystemAreaRespVO> getArea(@RequestParam("id") Integer id) {
        SystemAreaDO area = areaService.getArea(id);
        return success(BeanUtils.toBean(area, SystemAreaRespVO.class));
    }

    @GetMapping("/list-by-parent-id")
    @Operation(summary = "根据父级编号获得地区列表")
    @Parameter(name = "parentId", description = "父级编号", required = true, example = "110000")
    @PreAuthorize("@ss.hasPermission('system:area:query')")
    public CommonResult<List<SystemAreaRespVO>> getAreaListByParentId(@RequestParam("parentId") Integer parentId) {
        List<SystemAreaDO> list = areaService.getAreaListByParentId(parentId);
        return success(BeanUtils.toBean(list, SystemAreaRespVO.class));
    }

    @GetMapping("/list-by-type")
    @Operation(summary = "根据类型获得地区列表")
    @Parameter(name = "type", description = "地区类型", required = true, example = "2")
    @PreAuthorize("@ss.hasPermission('system:area:query')")
    public CommonResult<List<SystemAreaRespVO>> getAreaListByType(@RequestParam("type") Integer type) {
        List<SystemAreaDO> list = areaService.getAreaListByType(type);
        return success(BeanUtils.toBean(list, SystemAreaRespVO.class));
    }

    @GetMapping("/parent-areas")
    @Operation(summary = "获得指定地区的所有上级地区")
    @Parameter(name = "id", description = "地区编号", required = true, example = "110101")
    @PreAuthorize("@ss.hasPermission('system:area:query')")
    public CommonResult<List<SystemAreaRespVO>> getParentAreas(@RequestParam("id") Integer id) {
        List<SystemAreaDO> list = areaService.getParentAreas(id);
        return success(BeanUtils.toBean(list, SystemAreaRespVO.class));
    }

    @PostMapping("/init")
    @Operation(summary = "初始化地区数据")
    @PreAuthorize("@ss.hasPermission('system:area:init')")
    public CommonResult<Boolean> initAreaData() {
        areaService.initAreaData();
        return success(true);
    }

}