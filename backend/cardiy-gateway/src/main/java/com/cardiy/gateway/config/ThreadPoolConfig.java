package com.cardiy.gateway.config;

import cn.hutool.core.thread.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:ThreadPoolConfig
 * @Desc:线程池配置
 * @Author: liuchun
 * @CreateTime:2024/9/26 10:12
 * @Version:V1.30.0
 **/
@Configuration
public class ThreadPoolConfig {

    /**
     * @desc:用于处理restUrlLog的线程池
     * @author: liuchun
     * @createTime: 2024/9/26 11:12
     * @param: []
     * @version: V1.29.0
     * @return: java.util.concurrent.ThreadPoolExecutor
     **/
    @Bean("restUrlThreadPool")
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(4, 20, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),
                //线程工厂，线程名称前缀
                new NamedThreadFactory("thread-pool-rest-url-", false),
                //直接抛出RejectedExecutionException异常
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
