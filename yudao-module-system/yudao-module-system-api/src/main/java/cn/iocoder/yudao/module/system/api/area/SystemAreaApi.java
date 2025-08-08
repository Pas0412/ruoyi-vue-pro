package cn.iocoder.yudao.module.system.api.area;

import cn.iocoder.yudao.module.system.api.area.dto.SystemAreaRespDTO;

import java.util.List;

/**
 * 地区数据 API 接口
 *
 * @author 芋道源码
 */
public interface SystemAreaApi {

    /**
     * 获得地区数据
     *
     * @param id 地区编号
     * @return 地区数据
     */
    SystemAreaRespDTO getArea(Integer id);

    /**
     * 根据父级编号获得地区列表
     *
     * @param parentId 父级编号
     * @return 地区列表
     */
    List<SystemAreaRespDTO> getAreaListByParentId(Integer parentId);

    /**
     * 根据类型获得地区列表
     *
     * @param type 地区类型
     * @return 地区列表
     */
    List<SystemAreaRespDTO> getAreaListByType(Integer type);

    /**
     * 获得指定地区的所有上级地区（包括自身）
     *
     * @param id 地区编号
     * @return 上级地区列表，按地区类型降序排序
     */
    List<SystemAreaRespDTO> getParentAreas(Integer id);

}