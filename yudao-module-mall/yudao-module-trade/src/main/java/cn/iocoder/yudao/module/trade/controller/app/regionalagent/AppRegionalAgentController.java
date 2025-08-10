package cn.iocoder.yudao.module.trade.controller.app.regionalagent;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.agent.AppRegionalAgentApplyReqVO;
import cn.iocoder.yudao.module.trade.controller.app.regionalagent.vo.agent.AppRegionalAgentRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 用户 App - 地区代理
 *
 * @author 芋道源码
 */
@Tag(name = "用户 App - 地区代理")
@RestController
@RequestMapping("/trade/regional-agent")
@Validated
public class AppRegionalAgentController {

    @GetMapping("/get")
    @Operation(summary = "获得地区代理信息")
    public CommonResult<AppRegionalAgentRespVO> getRegionalAgent() {
        // TODO: 实现获取当前用户地区代理信息逻辑
        AppRegionalAgentRespVO respVO = new AppRegionalAgentRespVO();
        respVO.setIsAgent(false);
        respVO.setBrokeragePrice(0);
        respVO.setFrozenPrice(0);
        return success(respVO);
    }

    @PostMapping("/apply")
    @Operation(summary = "申请成为地区代理")
    public CommonResult<Boolean> applyRegionalAgent(@Valid @RequestBody AppRegionalAgentApplyReqVO applyReqVO) {
        // TODO: 实现申请成为地区代理逻辑
        return success(true);
    }

    @GetMapping("/check-area")
    @Operation(summary = "检查地区是否可申请")
    public CommonResult<Boolean> checkAreaAvailable(@RequestParam("provinceId") Integer provinceId,
                                                   @RequestParam(value = "cityId", required = false) Integer cityId,
                                                   @RequestParam(value = "areaId", required = false) Integer areaId,
                                                   @RequestParam("level") Integer level) {
        // TODO: 实现检查地区是否可申请逻辑
        return success(true);
    }

}