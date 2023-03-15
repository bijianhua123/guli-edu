package com.bijianhua.guli.service.oss.util;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    //endpoint: oss-cn-beijing.aliyuncs.com
    //keyid: LTAI5tPX5irvF8j3FdJp1c3v
    //keysecret: UhYOJw3ejwryA9aaZxgAtkg9yTrIhy
    //#bucket可以在控制台创建，也可以使用java代码创建，注意先测试bucket是否已被占用
    //bucketname: guli-file-bjh


    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;
}
