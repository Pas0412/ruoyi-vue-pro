package cn.iocoder.yudao.module.product.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collection;

@Schema(description = "管理后台 - 商品分类列表查询 Request VO")
@Data
public class ProductCategoryListReqVO {

    @Schema(description = "分类名称", example = "办公文具")
    private String name;

    @Schema(description = "开启状态", example = "0")
    private Integer status;

    @Schema(description = "父分类编号", example = "1")
    private Long parentId;

    @Schema(description = "父分类编号数组", example = "1,2,3")
    private Collection<Long> parentIds;

    public String getName() {
        return name;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getParentId() {
        return parentId;
    }

    public Collection<Long> getParentIds() {
        return parentIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setParentIds(Collection<Long> parentIds) {
        this.parentIds = parentIds;
    }

}
