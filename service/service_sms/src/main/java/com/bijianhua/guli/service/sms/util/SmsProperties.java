package com.bijianhua.guli.service.sms.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author bijianhua
 * @since 2023/3/18
 */
@Component
@Data
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties {

    private String regionId;
    private String keyId;
    private String keySecret;
    private String templateCode;
    private String signName;


}
