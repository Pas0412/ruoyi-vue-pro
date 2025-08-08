package cn.iocoder.yudao.module.system.api.area;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.api.area.dto.SystemAreaRespDTO;
import cn.iocoder.yudao.module.system.dal.dataobject.area.SystemAreaDO;
import cn.iocoder.yudao.module.system.service.area.SystemAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地区数据 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class SystemAreaApiImpl implements SystemAreaApi {

    @Resource
    private SystemAreaService areaService;

    @Override
    public SystemAreaRespDTO getArea(Integer id) {
        SystemAreaDO area = areaService.getArea(id);
        return BeanUtils.toBean(area, SystemAreaRespDTO.class);
    }

    @Override
    public List<SystemAreaRespDTO> getAreaListByParentId(Integer parentId) {
        List<SystemAreaDO> list = areaService.getAreaListByParentId(parentId);
        return BeanUtils.toBean(list, SystemAreaRespDTO.class);
    }

    @Override
    public List<SystemAreaRespDTO> getAreaListByType(Integer type) {
        List<SystemAreaDO> list = areaService.getAreaListByType(type);
        return BeanUtils.toBean(list, SystemAreaRespDTO.class);
    }

    @Override
    public List<SystemAreaRespDTO> getParentAreas(Integer id) {
        List<SystemAreaDO> list = areaService.getParentAreas(id);
        return BeanUtils.toBean(list, SystemAreaRespDTO.class);
    }

}