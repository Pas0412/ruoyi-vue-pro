package cn.iocoder.yudao.module.trade.controller.admin.areaagent.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 地区代理用户创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TradeAreaAgentUserCreateReqVO extends TradeAreaAgentUserBaseVO {

}