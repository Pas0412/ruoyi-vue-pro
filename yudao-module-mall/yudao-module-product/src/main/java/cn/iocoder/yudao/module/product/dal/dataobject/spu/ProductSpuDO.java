package cn.iocoder.yudao.module.product.dal.dataobject.spu;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.framework.mybatis.core.type.IntegerListTypeHandler;
import cn.iocoder.yudao.module.product.dal.dataobject.brand.ProductBrandDO;
import cn.iocoder.yudao.module.product.dal.dataobject.category.ProductCategoryDO;
import cn.iocoder.yudao.module.product.dal.dataobject.sku.ProductSkuDO;
import cn.iocoder.yudao.module.product.enums.spu.ProductSpuStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * 商品 SPU DO
 *
 * @author 芋道源码
 */
@TableName(value = "product_spu", autoResultMap = true)
@KeySequence("product_spu_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpuDO extends BaseDO {

    /**
     * 商品 SPU 编号，自增
     */
    @TableId
    private Long id;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Integer getSort() {
        return sort;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getPrice() {
        return price;
    }

    // ========== 基本信息 =========

    /**
     * 商品名称
     */
    private String name;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 商品简介
     */
    private String introduction;
    /**
     * 商品详情
     */
    private String description;

    /**
     * 商品分类编号
     *
     * 关联 {@link ProductCategoryDO#getId()}
     */
    private Long categoryId;
    /**
     * 商品品牌编号
     *
     * 关联 {@link ProductBrandDO#getId()}
     */
    private Long brandId;
    /**
     * 商品封面图
     */
    private String picUrl;
    /**
     * 商品轮播图
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> sliderPicUrls;

    /**
     * 排序字段
     */
    private Integer sort;
    /**
     * 商品状态
     *
     * 枚举 {@link ProductSpuStatusEnum}
     */
    private Integer status;

    // ========== SKU 相关字段 =========

    /**
     * 规格类型
     *
     * false - 单规格
     * true - 多规格
     */
    private Boolean specType;
    /**
     * 商品价格，单位使用：分
     *
     * 基于其对应的 {@link ProductSkuDO#getPrice()} sku单价最低的商品的
     */
    private Integer price;
    /**
     * 市场价，单位使用：分
     *
     * 基于其对应的 {@link ProductSkuDO#getMarketPrice()} sku单价最低的商品的
     */
    private Integer marketPrice;
    /**
     * 成本价，单位使用：分
     *
     * 基于其对应的 {@link ProductSkuDO#getCostPrice()} sku单价最低的商品的
     */
    private Integer costPrice;
    /**
     * 库存
     *
     * 基于其对应的 {@link ProductSkuDO#getStock()} 求和
     */
    private Integer stock;

    // ========== 物流相关字段 =========

    /**
     * 配送方式数组
     *
     * 对应 DeliveryTypeEnum 枚举
     */
    @TableField(typeHandler = IntegerListTypeHandler.class)
    private List<Integer> deliveryTypes;
    /**
     * 物流配置模板编号
     *
     * 对应 TradeDeliveryExpressTemplateDO 的 id 编号
     */
    private Long deliveryTemplateId;

    // ========== 营销相关字段 =========

    /**
     * 赠送积分
     */
    private Integer giveIntegral;

    // TODO @puhui999：字段估计要改成 brokerageType
    /**
     * 分销类型
     *
     * false - 默认
     * true - 自行设置
     */
    private Boolean subCommissionType;

    // ========== 统计相关字段 =========

    /**
     * 商品销量
     */
    private Integer salesCount;
    /**
     * 虚拟销量
     */
    private Integer virtualSalesCount;
    /**
     * 浏览量
     */
    private Integer browseCount;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public List<String> getSliderPicUrls() {
        return sliderPicUrls;
    }

    public void setSliderPicUrls(List<String> sliderPicUrls) {
        this.sliderPicUrls = sliderPicUrls;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getSpecType() {
        return specType;
    }

    public void setSpecType(Boolean specType) {
        this.specType = specType;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<Integer> getDeliveryTypes() {
        return deliveryTypes;
    }

    public void setDeliveryTypes(List<Integer> deliveryTypes) {
        this.deliveryTypes = deliveryTypes;
    }

    public Long getDeliveryTemplateId() {
        return deliveryTemplateId;
    }

    public void setDeliveryTemplateId(Long deliveryTemplateId) {
        this.deliveryTemplateId = deliveryTemplateId;
    }

    public Integer getGiveIntegral() {
        return giveIntegral;
    }

    public void setGiveIntegral(Integer giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

    public Boolean getSubCommissionType() {
        return subCommissionType;
    }

    public void setSubCommissionType(Boolean subCommissionType) {
        this.subCommissionType = subCommissionType;
    }

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }

    public Integer getVirtualSalesCount() {
        return virtualSalesCount;
    }

    public void setVirtualSalesCount(Integer virtualSalesCount) {
        this.virtualSalesCount = virtualSalesCount;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

}
