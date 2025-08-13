package cn.iocoder.yudao.module.product.controller.admin.regionalagent.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 地区代理提现分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegionalAgentWithdrawPageReqVO extends PageParam {

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "提现类型")
    private Integer type;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

    // 手动添加getter方法以解决Lombok编译问题
    public Long getUserId() {
        return userId;
    }

    public Integer getType() {
        return type;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime[] getCreateTime() {
        return createTime;
    }

}