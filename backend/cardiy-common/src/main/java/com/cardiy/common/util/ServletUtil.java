package com.cardiy.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @desc:Servlet工具类 注意该工具类只能在web环境下使用
 * 如果在非Web环境下调用这个方法，或者在没有任何请求上下文的线程中调用，会返回null
 * @author: liuchun
 * @createTime: 2024/11/6 14:09
 * @version: V1.32.0
 **/
public class ServletUtil {

    /**
     * @desc:获取request
     * @author: liuchun
     * @createTime: 2024/11/6 14:09
     * @version: V1.32.0
     **/
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * @desc:获取response
     * @author: liuchun
     * @createTime: 2024/11/6 14:09
     * @version: V1.32.0
     **/
    public static HttpServletResponse getResponse() {
        try {
            return getRequestAttributes().getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @desc:获取session
     * @author: liuchun
     * @createTime: 2024/11/6 14:09
     * @version: V1.32.0
     **/
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getCookie(String key) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * @desc:获取指定请求头
     * @author: liuchun
     * @createTime: 2024/11/6 14:09
     * @version: V1.32.0
     **/
    public static String getHeader(String headerName) {
        try {
            return getRequestAttributes().getRequest().getHeader(headerName);
        } catch (Exception e) {
            //如果有异常，说明是非Web环境下调用 获取request失败
            return "";
        }
    }

    /**
     * @desc:获取所有请求头
     * @author: liuchun
     * @createTime: 2024/11/6 14:09
     * @version: V1.32.0
     **/
    public static Map<String, String> getHeaders() {
        HttpServletRequest request = getRequestAttributes().getRequest();
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * @desc:获取requestAttributes
     * @author: liuchun
     * @createTime: 2024/11/6 14:09
     * @version: V1.32.0
     **/
    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }


    /**
     * 获取客户端IP
     */
    public static String getClientIp(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            InetSocketAddress remoteAddress = request.getRemoteAddress();
            if (remoteAddress != null) {
                InetAddress address = remoteAddress.getAddress();
                if (address != null) {
                    ip = address.getHostAddress();
                }
            }
        }
        // 处理多个IP的情况，取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip != null ? ip : "unknown";
    }
}
