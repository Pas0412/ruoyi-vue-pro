package cn.iocoder.yudao.module.system.dal.dataobject.area;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 地区数据 DO
 *
 * @author 芋道源码
 */
@TableName("system_area")
@KeySequence("system_area_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemAreaDO extends BaseDO {

    /**
     * 地区编号
     */
    @TableId
    private Integer id;
    /**
     * 地区名称
     */
    private String name;
    /**
     * 地区类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.system.enums.area.AreaTypeEnum}
     */
    private Integer type;
    /**
     * 父级编号
     */
    private Integer parentId;

}