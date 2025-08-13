package cn.iocoder.yudao.module.product.controller.admin.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 商品评价可见修改 Request VO")
@Data
@ToString(callSuper = true)
public class ProductCommentUpdateVisibleReqVO {

    @Schema(description = "评价编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15721")
    @NotNull(message = "评价编号不能为空")
    private Long id;

    @Schema(description = "是否可见", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    @NotNull(message = "是否可见不能为空")
    private Boolean visible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

}
