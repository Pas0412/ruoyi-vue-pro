package cn.iocoder.yudao.module.product.service.regionalagent.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 地区代理佣金增加 Request BO
 *
 * @author 芋道源码
 */
@Data
public class RegionalAgentAddReqBO {

    /**
     * 业务编号
     */
    @NotNull(message = "业务编号不能为空")
    private String bizId;
    /**
     * 标题
     */
    @NotNull(message = "标题不能为空")
    private String title;
    /**
     * 金额，单位：分
     */
    @NotNull(message = "金额不能为空")
    @Positive(message = "金额必须大于零")
    private Integer price;
    /**
     * 说明
     */
    private String description;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}