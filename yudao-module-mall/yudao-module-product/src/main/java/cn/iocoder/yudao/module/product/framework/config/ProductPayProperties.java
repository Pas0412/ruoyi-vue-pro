package cn.iocoder.yudao.module.product.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * 商品模块支付相关配置
 *
 * @author 芋道源码
 */
@ConfigurationProperties(prefix = "yudao.product.pay")
@Data
@Validated
public class ProductPayProperties {

    private static final String PAY_APP_KEY_DEFAULT = "mall";

    /**
     * 支付应用标识
     *
     * 在 pay 模块的 [支付管理 -> 应用信息] 里添加
     */
    @NotEmpty(message = "Pay 应用标识不能为空")
    private String payAppKey = PAY_APP_KEY_DEFAULT;

    public String getPayAppKey() {
        return payAppKey;
    }

    public void setPayAppKey(String payAppKey) {
        this.payAppKey = payAppKey;
    }

}