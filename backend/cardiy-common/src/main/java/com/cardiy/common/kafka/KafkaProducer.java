package com.cardiy.common.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author liuchun
 * @version v2.4.0
 * @className KafkaProducerService
 * @desc 生产者
 * @createTime 2026/1/7 16:45
 */
@Slf4j
@Component
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 发送消息（异步回调）
     */
    public void sendMessage(String topic, Object message) {
        try {
            CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topic, message);
            send.whenCompleteAsync((result, ex) -> {
                if (null == ex) {
                    log.info("KafkaProducer sendMsg success.topic: {}, msg: {}", topic, message);
                } else {
                    log.error("发送消息失败:" + ex.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("KafkaProducer sendMsg ===> 发生异常: {}", e.getMessage(), e);
        }
    }

    /**
     * 发送消息（带Key）
     */
    public void sendMessage(String topic, String key, Object message) {
        try {
            CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topic, key, message);
            send.whenCompleteAsync((result, ex) -> {
                if (null == ex) {
                    log.info("KafkaProducer sendMsg byKey success.topic: {},key:{}, msg: {}", topic, key, message);
                } else {
                    log.error("发送消息失败:" + ex.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("KafkaProducer sendMsg byKey ===> 发生异常: {}", e.getMessage(), e);
        }

    }

}
