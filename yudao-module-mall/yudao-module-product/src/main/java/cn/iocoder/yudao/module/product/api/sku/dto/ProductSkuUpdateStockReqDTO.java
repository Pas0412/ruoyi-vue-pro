package cn.iocoder.yudao.module.product.api.sku.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 商品 SKU 更新库存 Request DTO
 *
 * @author LeeYan9
 * @since 2022-08-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSkuUpdateStockReqDTO {

    /**
     * 商品 SKU
     */
    @NotNull(message = "商品 SKU 不能为空")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Data
    public static class Item {

        /**
         * 商品 SKU 编号
         */
        @NotNull(message = "商品 SKU 编号不能为空")
        private Long id;

        /**
         * 库存变化数量
         *
         * 正数：增加库存
         * 负数：扣减库存
         */
        @NotNull(message = "库存变化数量不能为空")
        private Integer incrCount;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getIncrCount() {
            return incrCount;
        }

        public void setIncrCount(Integer incrCount) {
            this.incrCount = incrCount;
        }

    }

}
