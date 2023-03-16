package com.bijianhua.guli.service.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bijianhua
 * @since 2023/3/16
 */

@SpringBootApplication
@ComponentScan({"com.bijianhua.guli"})
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCmsApplication.class, args);
    }
}
