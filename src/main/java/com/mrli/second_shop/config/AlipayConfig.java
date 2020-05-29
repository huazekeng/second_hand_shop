package com.mrli.second_shop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayConfig {

    private String url;

    private String app_id;

    private String app_private_key;

    private String alipay_public_key;

    private String format;

    private String charset;

    private String sign_type;

    private String notify_url;

}

