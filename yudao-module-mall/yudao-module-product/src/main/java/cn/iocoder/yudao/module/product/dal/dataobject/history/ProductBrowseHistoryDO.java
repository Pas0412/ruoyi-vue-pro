package cn.iocoder.yudao.module.product.dal.dataobject.history;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 商品浏览记录 DO
 *
 * @author owen
 */
@TableName("product_browse_history")
@KeySequence("product_browse_history_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBrowseHistoryDO extends BaseDO {

    /**
     * 记录编号
     */
    @TableId
    private Long id;
    /**
     * 商品 SPU 编号
     */
    private Long spuId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户是否删除
     */
    private Boolean userDeleted;

    // 手动添加getter方法以解决Lombok编译问题
    public Long getId() {
        return id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public Long getUserId() {
        return userId;
    }

    public Boolean getUserDeleted() {
        return userDeleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserDeleted(Boolean userDeleted) {
        this.userDeleted = userDeleted;
    }

}