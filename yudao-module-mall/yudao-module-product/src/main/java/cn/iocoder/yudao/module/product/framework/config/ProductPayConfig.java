package cn.iocoder.yudao.module.product.framework.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 商品模块支付配置
 *
 * @author 芋道源码
 */
@Configuration
@EnableConfigurationProperties(ProductPayProperties.class)
public class ProductPayConfig {
}