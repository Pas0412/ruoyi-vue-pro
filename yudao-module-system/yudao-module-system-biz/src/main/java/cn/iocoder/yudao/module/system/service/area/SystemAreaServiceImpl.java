package cn.iocoder.yudao.module.system.service.area;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.iocoder.yudao.module.system.dal.dataobject.area.SystemAreaDO;
import cn.iocoder.yudao.module.system.dal.mysql.area.SystemAreaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 地区数据 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class SystemAreaServiceImpl implements SystemAreaService {

    @Resource
    private SystemAreaMapper areaMapper;

    @Override
    public SystemAreaDO getArea(Integer id) {
        return areaMapper.selectById(id);
    }

    @Override
    public List<SystemAreaDO> getAreaList(List<Integer> ids) {
        return areaMapper.selectBatchIds(ids);
    }

    @Override
    public List<SystemAreaDO> getAreaListByParentId(Integer parentId) {
        return areaMapper.selectListByParentId(parentId);
    }

    @Override
    public List<SystemAreaDO> getAreaListByType(Integer type) {
        return areaMapper.selectListByType(type);
    }

    @Override
    public List<SystemAreaDO> getParentAreas(Integer areaId) {
        return areaMapper.selectParentAreas(areaId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initAreaData() {
        try {
            // 检查是否已经初始化过数据
            long count = areaMapper.selectCount();
            if (count > 0) {
                log.info("[initAreaData][地区数据已存在，跳过初始化]");
                return;
            }

            // 读取CSV文件
            ClassPathResource resource = new ClassPathResource("area.csv");
            InputStream inputStream = resource.getInputStream();
            String csvContent = IoUtil.read(inputStream, StandardCharsets.UTF_8);
            
            CsvReader reader = CsvUtil.getReader();
            List<String[]> rows = reader.read(csvContent);
            
            // 跳过标题行
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                if (row.length >= 4) {
                    SystemAreaDO area = SystemAreaDO.builder()
                            .id(Integer.parseInt(row[0]))
                            .name(row[1])
                            .type(Integer.parseInt(row[2]))
                            .parentId(Integer.parseInt(row[3]))
                            .build();
                    areaMapper.insert(area);
                }
            }
            
            log.info("[initAreaData][成功初始化地区数据，共{}条]", rows.size() - 1);
        } catch (Exception e) {
            log.error("[initAreaData][初始化地区数据失败]", e);
            throw new RuntimeException("初始化地区数据失败", e);
        }
    }

}