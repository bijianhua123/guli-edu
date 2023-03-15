package com.bijianhua.guli.service.vod.util;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Data
@ConfigurationProperties(prefix = "aliyun.vod")
public class VodProperties {

    private String keyid;
    private String keysecret;
    private String templateGroupId;
    private String workflowId;
}
