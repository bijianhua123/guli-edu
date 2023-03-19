package com.bijianhua.guli.service.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bijianhua
 * @since 2023/3/19
 */
@SpringBootApplication
@ComponentScan({"com.bijianhua.guli"})
@EnableDiscoveryClient //开启注册中心
@EnableFeignClients
public class ServiceTradeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTradeApplication.class, args);
    }
}
