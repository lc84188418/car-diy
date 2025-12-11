package com.cardiy.gateway;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages= {"com.cardiy"})
public class CardiyGatewayApplication {

    public static void main(String[] args) {
        System.setProperty("instanceId", RandomUtil.randomString(6));
        SpringApplication.run(CardiyGatewayApplication.class, args);
        log.info("Cardiy Gateway Application Started Successfully.instanceId:{}", System.getProperty("instanceId"));
    }
}