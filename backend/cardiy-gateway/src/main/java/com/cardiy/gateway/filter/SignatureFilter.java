package com.cardiy.gateway.filter;

import com.cardiy.common.constant.RespCodeEnum;
import com.cardiy.common.exception.BusinessException;
import com.cardiy.gateway.constant.RequestConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author liuchun
 * @version 1.0
 * .
 * @copyright 咪咕新空文化科技（厦门）有限公司
 * @data 2024/9/19 13:55
 * @since jdk 1.8
 */
@Slf4j
@Component
public class SignatureFilter implements GlobalFilter, Ordered {

    @Autowired
    private Environment environment;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        Set<String> skipFilters = exchange.getAttribute(CarConstant.SKIP_SIGN_FILTER_ATTR);
//        if (skipFilters != null && skipFilters.contains(CarConstant.COMMON_SIGN_FILTER_NAME)) {
//            return chain.filter(exchange);
//        }
        boolean testEnvironment = isTestEnvironment();
        ServerHttpRequest request = exchange.getRequest();
        String uriPath = request.getURI().getPath();
        HttpHeaders headers = request.getHeaders();
        //参数加解密 默认1，生产环境必须加密
        String encrypt = headers.getFirst(RequestConstant.ENCRYPT);
        String reqId = headers.getFirst(RequestConstant.REQ_ID);
        String timestamp = headers.getFirst(RequestConstant.TIMESTAMP);
        String signature = headers.getFirst(RequestConstant.SIGNATURE);

        //必传请求头校验
//        doValidHeaders(encrypt, reqId, timestamp, signature);

        //添加reqId、时间戳有效性验证
//        signatureValidUtil.validReqIdAndTimestamp(reqId, timestamp);
        String token = headers.getFirst(RequestConstant.TOKEN);
        //联调环境，可忽略签名migu/hound2024，ture为不校验签名
        boolean signatureFlag = Objects.equals(headers.getFirst("disableSignature"), "cardiy") && testEnvironment;

        // 签名校验 & 参数解密（encrypt=1）
//        return signatureValidUtil.signatureValidAndEncrypt(exchange, chain, token, request, signatureFlag, headers);
        return chain.filter(exchange);

    }

    /**
     * @author liuchun
     * 必传请求头校验
     * @date 11:03 2025/12/5
     * @param [encrypt, reqId, timestamp, signature]
     * @version v2.2.0
     * @return void
     **/
    private static void doValidHeaders(String encrypt, String reqId, String timestamp, String signature) {
        if(!StringUtils.hasText(encrypt)){
            throw new BusinessException(RespCodeEnum.HTTP_HEADER_MISSING,RequestConstant.ENCRYPT);
        }
        if(!StringUtils.hasText(reqId)){
            throw new BusinessException(RespCodeEnum.HTTP_HEADER_MISSING,RequestConstant.REQ_ID);
        }
        if(!StringUtils.hasText(timestamp)){
            throw new BusinessException(RespCodeEnum.HTTP_HEADER_MISSING,RequestConstant.TIMESTAMP);
        }
        if(!StringUtils.hasText(signature)){
            throw new BusinessException(RespCodeEnum.HTTP_HEADER_MISSING,RequestConstant.SIGNATURE);
        }
    }

    private boolean isTestEnvironment() {
        return environment.matchesProfiles("local","dev","test");
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
