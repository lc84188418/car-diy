package com.cardiy.gateway.filter;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {


    @Resource(name = "restUrlThreadPool")
    private ThreadPoolExecutor restUrlThreadPool;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String methodName = request.getMethod().name();
        String uri = request.getURI().getPath();
        HttpHeaders headers = request.getHeaders();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        
        log.info("{} {} request headers: {}", methodName, uri, headers);
        log.info("{} {} request queryParams: {}", methodName, uri, queryParams);
        
        // 只处理有 body 的请求（POST、PUT、PATCH 等）
        if (request.getHeaders().getContentLength() > 0) {
            return DataBufferUtils.join(request.getBody())
                    .flatMap(dataBuffer -> {
                        // 读取 body 内容
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        String bodyStr = new String(bytes, StandardCharsets.UTF_8);
                        
                        // 记录 body 内容
                        log.info("{} {} request bodyParams: {}", methodName, uri, bodyStr);
                        
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
            return chain.filter(exchange);
        }
    }


    @Override
    public int getOrder() {
        return 1;
    }

}
