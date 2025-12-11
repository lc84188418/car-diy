package com.cardiy.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.cardiy.common.util.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName:RestUrlLogFilter
 * @Desc:REST接口url调用日志统计处理过滤器
 * @Author: liuchun
 * @CreateTime:2024/9/20 17:16
 * @Version:V1.31.0
 **/
@Slf4j
@Component
public class RestUrlLogFilter implements GlobalFilter, Ordered {

//    @Resource
//    private KafkaProducer kafkaProducer;

    @Resource(name = "restUrlThreadPool")
    private ThreadPoolExecutor restUrlThreadPool;

//    @Value("${platform.kafka.url.visit.topic}")
//    private String TOPIC_URL_LOG;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            //方法名
            String methodName = serverHttpRequest.getMethod().name();
            //uri
            String uri = serverHttpRequest.getURI().getPath();
            Integer[] currentDateByYMD = DateUtil.getIntegerDateByYMD();
            //封装消息体
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", uri);
            jsonObject.put("methodName", methodName);
            jsonObject.put("day", currentDateByYMD[2]);
            jsonObject.put("month", currentDateByYMD[1]);
            jsonObject.put("year", currentDateByYMD[0]);
            jsonObject.put("isStandard", isStandardRestUrl(uri) ? 1 : 0);
            //是否内部调用，1是0否,此处为内部调用，如果是调用其他第三方接口，则为外部调用0
            jsonObject.put("isInner", 1);
            log.info("RestUrlLogFilter filter ===> 发送消息: {}", jsonObject);
//            CompletableFuture.runAsync(() -> kafkaProducer.sendMsg(TOPIC_URL_LOG, jsonObject.toJSONString()), restUrlThreadPool);
        } catch (Exception e) {
            log.error("RestUrlLogFilter filter ===> 发生异常: {}", e.getMessage(), e);
        }
        return chain.filter(exchange);
    }

    /**
     * @desc:是否是标准的REST接口url（不能以/结尾并且不能含有//）
     * @author: liuchun
     * @createTime: 2024/9/27 17:37
     * @param: [uri]
     * @version: V1.31.0
     * @return: boolean
     **/
    private boolean isStandardRestUrl(String uri) {
        return !uri.endsWith("/") && !uri.contains("//");
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
