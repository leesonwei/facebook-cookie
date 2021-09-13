package com.anywhere.facebookcookie.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lizong
 * @createTime 2021-02-02
 * @Description TODO
 * @since 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.cookie")
public class CookieProperties {
    private String url;
    private String cookieKey;
    private String fileName;
    private Boolean customerProxy;
    private String proxyHost;
    private Integer proxyPort;
    private Integer threadNum;
    private Integer intervalMs;
}
