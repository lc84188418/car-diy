package com.cardiy.render;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages= {"com.cardiy"})
public class CardiyRenderApplication {

    public static void main(String[] args) {
        System.setProperty("instanceId", RandomUtil.randomString(6));
        SpringApplication.run(CardiyRenderApplication.class, args);
        log.info("Cardiy Render Application Started Successfully.instanceId:{}", System.getProperty("instanceId"));
    }
}


