package cn.iocoder.yudao.module.system.dal.mysql.area;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.area.SystemAreaDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地区数据 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SystemAreaMapper extends BaseMapperX<SystemAreaDO> {

    /**
     * 根据父级编号查询地区列表
     *
     * @param parentId 父级编号
     * @return 地区列表
     */
    default List<SystemAreaDO> selectListByParentId(Integer parentId) {
        return selectList("parent_id", parentId);
    }

    /**
     * 根据类型查询地区列表
     *
     * @param type 地区类型
     * @return 地区列表
     */
    default List<SystemAreaDO> selectListByType(Integer type) {
        return selectList("type", type);
    }

    /**
     * 根据地区编号查询所有上级地区（包含自己）
     *
     * @param areaId 地区编号
     * @return 地区列表（从县区到省的顺序）
     */
    List<SystemAreaDO> selectParentAreas(@Param("areaId") Integer areaId);

}