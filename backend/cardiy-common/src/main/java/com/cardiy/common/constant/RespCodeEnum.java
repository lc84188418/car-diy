package com.cardiy.common.constant;

import lombok.Getter;

@Getter
public enum RespCodeEnum {
    /**
     * 公共统一状态码.
     */
    OK("200", "OK"),
    BAD_REQUEST("400", "Bad Request"),
    NOT_LOG_IN("401", "未登录"),
    REQUEST_METHOD_INVALID("402", "请求方式错误"),
    SESSION_INVALID_OR_EXPIRED("403", "Session Invalid Or Expired"),
    REQUEST_INEXISTENCE("404", "请求服务不存在"),
    REQUEST_BODY_MISSING("405", "请求body缺失"),
    HTTP_HEADER_MISSING("406", "请求头必传参数缺失"),
    INVALID_PARAMS("407", "参数错误"),
    UNAUTHORIZED("408", "未授权"),
    ILLEGAL_SIGNATURE("413", "无效签名"),

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    INTERNAL_SERVER_MESSAGE_NULL_ERROR("501", "内部服务响应为空"),
    INTERNAL_SERVER_MESSAGE_ERROR("502", "内部服务响应失败"),
    INVALID_ACCESS("503", "Invalid Access"),
    FREQUENT_ACCESS("504", "操作太频繁了，喝口水吧~"),
    IP_FORBIDDEN_ACCESS("505", "该ip被禁止访问"),
    USER_FORBIDDEN_ACCESS("506", "该用户被禁止访问"),
    INTERNAL_SERVER_RELEGATE("507", "内部服务降级"),
    FILTER_DECRYPT_ERROR("507", "参数解码失败"),
    REQUEST_ILLEGAL("509", "非法请求"),
    RESPONSE_ENCRYPT_FAIL("510", "响应结果加密失败"),

    BUSINESS_EXCEPTION("600", "业务异常")
    ;

    private String code;

    private String message;

    RespCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
