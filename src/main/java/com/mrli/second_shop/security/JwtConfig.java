package com.mrli.second_shop.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt配置信息
 */
@ConfigurationProperties(prefix = "audience")
@Component
@Data
public class JwtConfig {

    private String header;
    private String tokenHead;
    private String clientId;
    private String base64Secret;
    private String name;
    private long expiresSecond;
    private String[] whiteList;
    private long refreshExpiresSecond;

}
