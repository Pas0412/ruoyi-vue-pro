package cn.iocoder.yudao.module.system.api.area.dto;

import lombok.*;

/**
 * 地区数据 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class SystemAreaRespDTO {

    /**
     * 地区编号
     */
    private Integer id;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 地区类型
     */
    private Integer type;

    /**
     * 父级编号
     */
    private Integer parentId;

}