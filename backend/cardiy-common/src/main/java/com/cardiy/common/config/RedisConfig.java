package com.cardiy.common.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ge.fangyu
 * @version 1.0
 * @description：redis相关配置.
 * @copyright 咪咕新空文化科技（厦门）有限公司
 * @data 2023-04-20 10:43:44
 * @since jdk 1.8
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Value("${spring.redis.prefix:}")
    private String redisPrefix;

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        StringRedisSerializer stringKeyRedisSerializer = new StringRedisSerializer() {
            @Override
            public byte[] serialize(String s) {
                if (s == null) {
                    return null;
                }
                //这里加上你需要加上的key前缀
                String realKey = redisPrefix + ":" + s;
                log.info("=> real redis key: {}", realKey);
                return super.serialize(realKey);
            }
        };
        // key采用String的序列化方式
        template.setKeySerializer(stringKeyRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringKeyRedisSerializer);

        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // value序列化方式采用fastJson
        template.setValueSerializer(fastJsonRedisSerializer);
        // hash的value序列化方式采用fastJson
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
