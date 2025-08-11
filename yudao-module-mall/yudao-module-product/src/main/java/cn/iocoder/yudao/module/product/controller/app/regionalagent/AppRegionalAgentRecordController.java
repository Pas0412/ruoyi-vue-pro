package cn.iocoder.yudao.module.product.controller.app.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import javax.annotation.security.PermitAll;
import cn.iocoder.yudao.module.product.controller.app.regionalagent.vo.*;
import cn.iocoder.yudao.module.product.convert.regionalagent.RegionalAgentRecordConvert;
import cn.iocoder.yudao.module.product.dal.dataobject.regionalagent.RegionalAgentRecordDO;
import cn.iocoder.yudao.module.product.service.regionalagent.RegionalAgentRecordService;
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
 * 用户 APP - 地区代理记录 RESTful API
 *
 * @author 芋道源码
 */
@Tag(name = "用户 APP - 地区代理记录")
@RestController
@RequestMapping("/product/regional-agent-record")
@Validated
@Slf4j
public class AppRegionalAgentRecordController {

    @Resource
    private RegionalAgentRecordService regionalAgentRecordService;

    @GetMapping("/get")
    @Operation(summary = "获得地区代理记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PermitAll
    public CommonResult<AppRegionalAgentRecordRespVO> getRegionalAgentRecord(@RequestParam("id") Long id) {
        RegionalAgentRecordDO regionalAgentRecord = regionalAgentRecordService.getRegionalAgentRecord(id);
        return success(RegionalAgentRecordConvert.INSTANCE.convertApp(regionalAgentRecord));
    }

    @GetMapping("/page")
    @Operation(summary = "获得地区代理记录分页")
    @PermitAll
    public CommonResult<PageResult<AppRegionalAgentRecordRespVO>> getRegionalAgentRecordPage(@Valid AppRegionalAgentRecordPageReqVO pageVO) {
        PageResult<RegionalAgentRecordDO> pageResult = regionalAgentRecordService.getRegionalAgentRecordPage(RegionalAgentRecordConvert.INSTANCE.convert(pageVO, getLoginUserId()));
        return success(RegionalAgentRecordConvert.INSTANCE.convertAppPage(pageResult));
    }

}