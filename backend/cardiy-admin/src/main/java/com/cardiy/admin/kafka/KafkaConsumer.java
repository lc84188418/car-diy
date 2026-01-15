package com.cardiy.admin.kafka;

import com.alibaba.fastjson.JSONObject;
import com.cardiy.admin.domain.CommonUrlLogEntity;
import com.cardiy.admin.domain.SysOperLog;
import com.cardiy.admin.service.CommonUrlLogService;
import com.cardiy.admin.service.ISysOperLogService;
import com.cardiy.common.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.*;

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

    @Resource
    private ISysOperLogService sysOperLogService;
    @Resource
    private CommonUrlLogService commonUrlLogService;

    /**
     * 单条消息消费
     */
    @KafkaListener(topics = "${topic.systemOperLog}", groupId = "system-group")
    public void consumeOperLogMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            String key = record.key();
            String message = record.value();
            long offset = record.offset();
            int partition = record.partition();
            log.info("received msg.key: {}, offset: {}, partition: {}, content: {}",
                    key, offset, partition, message);

            // 业务处理
            processOperLogMessage(message);

            // 手动提交偏移量
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("处理消息失败: {}", record, e);
            // 可以根据业务需求决定是否提交
        }
    }

    @KafkaListener(topics = "${topic.urlVisit}", batch = "true")
    public void consume(List<ConsumerRecord<String, String>> records, Acknowledgment acknowledgment) {
        try {
            Map<String, Long> map = new HashMap<>();
            Set<CommonUrlLogEntity> set = new HashSet<>();
            if (records != null && records.size() > 1) {
                log.info("批量消费{}条消息", records.size());
            }
            //1处理每个url的数量
            for (ConsumerRecord<String, String> record : records) {
                String msg = record.value();
                log.info("product_platform_request_url_log  ===> 消费到消息: {}", JsonUtil.getJsonSubString(msg));
                CommonUrlLogEntity commonUrlLog = JSONObject.parseObject(msg, CommonUrlLogEntity.class);
                String key = getMapKey(commonUrlLog);
                map.merge(key, 1L, Long::sum);
                set.add(commonUrlLog);
            }
            //2处理每个url的数量,将数量设置到对应的对象中,并批量更新
            for (CommonUrlLogEntity commonUrlLog : set) {
                commonUrlLog.setCount(map.get(getMapKey(commonUrlLog)));
            }
            commonUrlLogService.updateBatch(set);
            // 所有消息处理完毕后提交偏移量（手动模式用）
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("product_platform_request_url_log  ===> 发生异常: {}", e.getMessage(), e);
        }
    }

    /**
     * @desc:获取map的key
     * @author: liuchun
     * @createTime: 2024/9/23 1:55
     * @param: commonUrlLog url日志对象
     * @version: V1.29.0
     * @return: java.lang.String 存放在map中的key
     **/
    private String getMapKey(CommonUrlLogEntity commonUrlLog) {
        return commonUrlLog.getUrl() + commonUrlLog.getDay();
    }

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
    private void processOperLogMessage(String msg) {
        SysOperLog sysOperLog = JSONObject.parseObject(msg, SysOperLog.class);
        //入库
        sysOperLogService.save(sysOperLog);
    }
}
