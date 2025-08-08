package cn.iocoder.yudao.module.system.controller.admin.area.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 地区数据 Response VO")
@Data
public class SystemAreaRespVO {

    @Schema(description = "地区编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Integer id;

    @Schema(description = "地区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京市")
    private String name;

    @Schema(description = "地区类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer type;

    @Schema(description = "父级编号", example = "100000")
    private Integer parentId;

}