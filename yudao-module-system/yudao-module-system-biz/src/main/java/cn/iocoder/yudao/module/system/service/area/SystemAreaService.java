package cn.iocoder.yudao.module.system.service.area;

import cn.iocoder.yudao.module.system.dal.dataobject.area.SystemAreaDO;

import java.util.List;

/**
 * 地区数据 Service 接口
 *
 * @author 芋道源码
 */
public interface SystemAreaService {

    /**
     * 获得地区数据
     *
     * @param id 编号
     * @return 地区数据
     */
    SystemAreaDO getArea(Integer id);

    /**
     * 获得地区数据列表
     *
     * @param ids 编号
     * @return 地区数据列表
     */
    List<SystemAreaDO> getAreaList(List<Integer> ids);

    /**
     * 根据父级编号获得地区数据列表
     *
     * @param parentId 父级编号
     * @return 地区数据列表
     */
    List<SystemAreaDO> getAreaListByParentId(Integer parentId);

    /**
     * 根据类型获得地区数据列表
     *
     * @param type 地区类型
     * @return 地区数据列表
     */
    List<SystemAreaDO> getAreaListByType(Integer type);

    /**
     * 根据地区编号获得所有上级地区（包含自己）
     *
     * @param areaId 地区编号
     * @return 地区列表（从县区到省的顺序）
     */
    List<SystemAreaDO> getParentAreas(Integer areaId);

    /**
     * 初始化地区数据
     * 从area.csv文件导入数据到数据库
     */
    void initAreaData();

}