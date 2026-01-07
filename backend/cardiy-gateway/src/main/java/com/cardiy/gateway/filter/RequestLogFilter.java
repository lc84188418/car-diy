package com.cardiy.gateway.filter;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.cardiy.common.kafka.KafkaProducer;
import com.cardiy.common.util.ServletUtil;
import com.cardiy.common.util.UserAgentUtil;
import com.cardiy.gateway.util.LogUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {


    @Resource(name = "restUrlThreadPool")
    private ThreadPoolExecutor restUrlThreadPool;
    @Resource()
    private LogUtil logUtil;

    @Resource
    private KafkaProducer kafkaProducer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String methodName = request.getMethod().name();
        String uri = request.getURI().getPath();
        HttpHeaders headers = request.getHeaders();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        // 获取客户端IP
        String clientIp = ServletUtil.getClientIp(request);

        // 记录请求信息
        log.info("{} {} request headers: {}", methodName, uri, headers);
        if (!queryParams.isEmpty()) {
            log.info("{} {} request queryParams: {}", methodName, uri, queryParams);
        }

        // 构建操作日志对象
        JSONObject operLog = new JSONObject();
        //优先从请求头中获取，若无则使用雪花算法的自增ID
        String reqId = headers.getFirst("reqId");
        String userAgent = headers.getFirst("user-agent");
        operLog.put("operId", StringUtils.hasText(reqId) ? reqId : IdUtil.getSnowflakeNextIdStr());
        operLog.put("requestMethod", methodName);
        operLog.put("uri", uri);
        operLog.put("ip", clientIp);
        operLog.put("time", new Date());
        // 默认正常
        operLog.put("respCode", 200);
        // 设置业务模块（从URI推断）
        operLog.put("title", logUtil.extractTitleFromUri(uri));
        UserAgentUtil.DeviceInfo deviceInfo = UserAgentUtil.parseUserAgent(userAgent);
        log.info("deviceInfo: {}", deviceInfo);
        operLog.put("userAgent", JSONObject.toJSONString(deviceInfo));

        // 构建请求参数字符串
        StringBuilder paramBuilder = new StringBuilder();
        if (!queryParams.isEmpty()) {
            paramBuilder.append("QueryParams: ").append(queryParams);
        }

        // 只处理有 body 的请求（POST、PUT、PATCH 等）
        if (request.getHeaders().getContentLength() > 0) {
            MediaType mediaType = headers.getContentType();
            operLog.put("contentType", mediaType.getType() + "/" + mediaType.getSubtype());

            return DataBufferUtils.join(request.getBody())
                    .flatMap(dataBuffer -> {
                        // 读取 body 内容
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        String bodyStr = new String(bytes, StandardCharsets.UTF_8);

                        // 记录 body 内容
                        log.info("{} {} request bodyParams: {}", methodName, uri, bodyStr);

                        if (!paramBuilder.isEmpty()) {
                            paramBuilder.append("; ");
                        }
                        paramBuilder.append("Body: ").append(bodyStr);

                        // 释放 buffer
                        DataBufferUtils.release(dataBuffer);

                        // 重新构建请求，因为 body 流已经被消费了
                        ServerHttpRequestDecorator decoratedRequest = new ServerHttpRequestDecorator(request) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                if (bytes.length > 0) {
                                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                                    return Flux.just(buffer);
                                }
                                return Flux.empty();
                            }
                        };

                        // 使用装饰后的请求继续过滤链
                        return chain.filter(exchange.mutate().request(decoratedRequest).build());
                    })
                    .onErrorResume(e -> {
                        log.error("{} {} 读取请求体失败: {}", methodName, uri, e.getMessage(), e);
                        // 如果读取失败，继续执行（不阻塞请求）
                        return chain.filter(exchange);
                    });
        } else {
            // 没有 body 的请求直接继续
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // 打印响应信息
//                System.out.println("Response Status Code: " + response.getStatusCode());
                //HTTP状态码
                operLog.put("httpStatusCode", exchange.getResponse().getStatusCode().value());
                long executeTime = DateUtil.between(operLog.getDate("time"), new Date(), DateUnit.MS);
                operLog.put("executeTime", executeTime);
                log.info("operLog info: {}", operLog);
                // 注意：响应体通常在发送后无法直接访问，除非你有特定的逻辑去捕获它，例如使用Buffer-ClientResponseBody。
                //TODO LIUCHUN 拿到code和msg，放到operLog中
                kafkaProducer.sendMessage("system-oper-log", operLog.toJSONString());
            }));
        }
    }


    @Override
    public int getOrder() {
        return 1;
    }

}
