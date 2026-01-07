package com.cardiy.common.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author liuchun
 * @version v2.4.0
 * @className KafkaConsumer
 * @desc 消费者
 * @createTime 2026/1/7 16:52
 */
@Slf4j
@Component
public class KafkaConsumer {
    /**
     * 单条消息消费
     */
    @KafkaListener(topics = "system-oper-log", groupId = "system-group")
    public void consumeUserMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            String key = record.key();
            String message = record.value();
            long offset = record.offset();
            int partition = record.partition();
            log.info("收到用户消息，key: {}, offset: {}, partition: {}, 内容: {}",
                    key, offset, partition, message);

            // 业务处理
            processUserMessage(message);

            // 手动提交偏移量
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("处理消息失败: {}", record, e);
            // 可以根据业务需求决定是否提交
        }
    }

    /**
     * 批量消息消费
     */
//    @KafkaListener(topics = "system-oper-log", groupId = "system-batch-group", containerFactory = "batchFactory")
//    public void consumeBatchMessages(List<ConsumerRecord<String, Object>> records, Acknowledgment acknowledgment) {
//        log.info("收到批量消息，数量: {}", records.size());
//        for (ConsumerRecord<String, Object> record : records) {
//            try {
//                processUserMessage(record.value());
//            } catch (Exception e) {
//                log.error("处理单条消息失败: {}", record, e);
//            }
//        }
//        // 批量提交
//        acknowledgment.acknowledge();
//    }

    /**
     * 使用注解方式获取消息头
     */
//    @KafkaListener(topics = "user-topic", groupId = "header-group")
//    public void consumeWithHeaders(
//            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//            @Header(KafkaHeaders.PARTITION) int partition,
//            @Header(KafkaHeaders.OFFSET) long offset,
//            @Header(KafkaHeaders.RECEIVED_KEY) String key,
//            Object message) {
//
//        log.info("收到消息，topic: {}, partition: {}, offset: {}, key: {}, 内容: {}",
//                topic, partition, offset, key, message);
//
//        processUserMessage(message);
//    }
    private void processUserMessage(Object message) {
        // 模拟业务处理
        log.info("业务处理: {}", message);
    }
}
