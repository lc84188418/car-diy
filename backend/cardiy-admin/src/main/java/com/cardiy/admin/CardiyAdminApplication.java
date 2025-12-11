package com.cardiy.admin;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Slf4j
@EnableDiscoveryClient
@EnableMongoAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages= {"com.cardiy"})
public class CardiyAdminApplication {

    public static void main(String[] args) {
        System.setProperty("instanceId", RandomUtil.randomString(6));
        SpringApplication.run(CardiyAdminApplication.class, args);
        log.info("Cardiy Admin Application Started Successfully.instanceId:{}", System.getProperty("instanceId"));
    }
}


