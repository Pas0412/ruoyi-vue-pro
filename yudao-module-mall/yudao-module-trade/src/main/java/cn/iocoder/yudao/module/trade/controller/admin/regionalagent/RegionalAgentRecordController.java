package cn.iocoder.yudao.module.trade.controller.admin.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.record.RegionalAgentRecordPageReqVO;
import cn.iocoder.yudao.module.trade.controller.admin.regionalagent.vo.record.RegionalAgentRecordRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 地区代理佣金记录
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 地区代理佣金记录")
@RestController
@RequestMapping("/trade/regional-agent/record")
@Validated
public class RegionalAgentRecordController {

    @GetMapping("/page")
    @Operation(summary = "获得地区代理佣金记录分页")
    public CommonResult<PageResult<RegionalAgentRecordRespVO>> getRegionalAgentRecordPage(@Valid RegionalAgentRecordPageReqVO pageReqVO) {
        // TODO: 实现地区代理佣金记录分页查询逻辑
        return success(new PageResult<>());
    }

    @GetMapping("/get")
    @Operation(summary = "获得地区代理佣金记录")
    public CommonResult<RegionalAgentRecordRespVO> getRegionalAgentRecord(@RequestParam("id") Long id) {
        // TODO: 实现获取地区代理佣金记录详情逻辑
        return success(new RegionalAgentRecordRespVO());
    }

}