package com.cardiy.common.constant;

/**
 * @author liuchun
 * @version v2.4.0
 * @className RedisConstant
 * @desc TODO
 * @createTime 2026/1/15 17:07
 */
public class RedisConstant {

    /**
     * 系统字典数据缓存key
     * {0}:dictType
     */
    public static final String SYS_DICT_DATA_KEY = "sys:dict_data:{0}";
    public static final String SYS_DICT_DATA_PREFIX = "sys:dict_data:";

    /**
     * 分布式锁url请求日志统计
     * {0}:methodName,{1}:url
     */
    public static final String DL_USER_URL_LOG_KEY = "user:dl:url_log:{0}-{1}";

}
