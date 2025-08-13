package cn.iocoder.yudao.module.product.dal.dataobject.comment;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.product.dal.dataobject.sku.ProductSkuDO;
import cn.iocoder.yudao.module.product.dal.dataobject.spu.ProductSpuDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品评论 DO
 *
 * @author 芋道源码
 */
@TableName(value = "product_comment", autoResultMap = true)
@KeySequence("product_comment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommentDO extends BaseDO {

    /**
     * 默认匿名昵称
     */
    public static final String NICKNAME_ANONYMOUS = "匿名用户";

    /**
     * 评论编号，主键自增
     */
    @TableId
    private Long id;

    /**
     * 评价人的用户编号
     *
     * 关联 MemberUserDO 的 id 编号
     */
    private Long userId;
    /**
     * 评价人名称
     */
    private String userNickname;
    /**
     * 评价人头像
     */
    private String userAvatar;
    /**
     * 是否匿名
     */
    private Boolean anonymous;

    /**
     * 交易订单编号
     *
     * 关联 TradeOrderDO 的 id 编号
     */
    private Long orderId;
    /**
     * 交易订单项编号
     *
     * 关联 TradeOrderItemDO 的 id 编号
     */
    private Long orderItemId;

    /**
     * 商品 SPU 编号
     *
     * 关联 {@link ProductSpuDO#getId()}
     */
    private Long spuId;
    /**
     * 商品 SPU 名称
     *
     * 关联 {@link ProductSpuDO#getName()}
     */
    private String spuName;
    /**
     * 商品 SKU 编号
     *
     * 关联 {@link ProductSkuDO#getId()}
     */
    private Long skuId;
    /**
     * 商品 SKU 图片地址
     *
     * 关联 {@link ProductSkuDO#getPicUrl()}
     */
    private String skuPicUrl;
    /**
     * 属性数组，JSON 格式
     *
     * 关联 {@link ProductSkuDO#getProperties()}
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<ProductSkuDO.Property> skuProperties;

    /**
     * 是否可见
     *
     * true:显示
     * false:隐藏
     */
    private Boolean visible;
    /**
     * 评分星级
     *
     * 1-5 分
     */
    private Integer scores;
    /**
     * 描述星级
     *
     * 1-5 星
     */
    private Integer descriptionScores;
    /**
     * 服务星级
     *
     * 1-5 星
     */
    private Integer benefitScores;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论图片地址数组
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> picUrls;

    /**
     * 商家是否回复
     */
    private Boolean replyStatus;
    /**
     * 回复管理员编号
     * 关联 AdminUserDO 的 id 编号
     */
    private Long replyUserId;
    /**
     * 商家回复内容
     */
    private String replyContent;
    /**
     * 商家回复时间
     */
    private LocalDateTime replyTime;

    // 手动添加getter方法以解决Lombok编译问题
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public String getSpuName() {
        return spuName;
    }

    public Long getSkuId() {
        return skuId;
    }

    public String getSkuPicUrl() {
        return skuPicUrl;
    }

    public List<ProductSkuDO.Property> getSkuProperties() {
        return skuProperties;
    }

    public Boolean getVisible() {
        return visible;
    }

    public Integer getScores() {
        return scores;
    }

    public Integer getDescriptionScores() {
        return descriptionScores;
    }

    public Integer getBenefitScores() {
        return benefitScores;
    }

    public String getContent() {
        return content;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public Boolean getReplyStatus() {
        return replyStatus;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public LocalDateTime getReplyTime() {
        return replyTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public void setSkuPicUrl(String skuPicUrl) {
        this.skuPicUrl = skuPicUrl;
    }

    public void setSkuProperties(List<ProductSkuDO.Property> skuProperties) {
        this.skuProperties = skuProperties;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public void setDescriptionScores(Integer descriptionScores) {
        this.descriptionScores = descriptionScores;
    }

    public void setBenefitScores(Integer benefitScores) {
        this.benefitScores = benefitScores;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    public void setReplyStatus(Boolean replyStatus) {
        this.replyStatus = replyStatus;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }

}
