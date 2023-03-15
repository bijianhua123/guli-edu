package com.bijianhua.guli.service.vod;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.bijianhua.guli"})
public class ServiceVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class, args);
    }
}
