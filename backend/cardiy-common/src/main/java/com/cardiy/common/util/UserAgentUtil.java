package com.cardiy.common.util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * 浏览器和操作系统检测工具类
 */
public class UserAgentUtil {

    /**
     * 从当前请求获取设备信息
     */
    public static DeviceInfo getDeviceInfo() {
        return parseUserAgent(ServletUtil.getRequest());
    }

    /**
     * 从指定 User-Agent 字符串解析设备信息
     */
    public static DeviceInfo parseUserAgent(String userAgentString) {
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        return extractDeviceInfo(userAgent);
    }

    /**
     * 从 HttpServletRequest 解析设备信息
     */
    public static DeviceInfo parseUserAgent(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");
        return parseUserAgent(userAgentString);
    }

    /**
     * 提取设备信息
     */
    private static DeviceInfo extractDeviceInfo(UserAgent userAgent) {
        DeviceInfo info = new DeviceInfo();

        // 浏览器信息
        Browser browser = userAgent.getBrowser();
        Version version = userAgent.getBrowserVersion();

        info.setBrowserName(browser.getName());
        info.setBrowserType(browser.getBrowserType().getName());
        info.setBrowserVersion(version != null ? version.getVersion() : "Unknown");
        info.setBrowserGroup(browser.getGroup().getName());
        info.setBrowserManufacturer(browser.getManufacturer().getName());

        // 操作系统信息
        OperatingSystem os = userAgent.getOperatingSystem();

        info.setOsName(os.getName());
        info.setOsDeviceType(os.getDeviceType().getName());
        info.setOsGroup(os.getGroup().getName());
        info.setOsManufacturer(os.getManufacturer().getName());

        // 是否为移动设备
        info.setMobile(os.isMobileDevice());

        return info;
    }

    /**
     * 获取当前请求的 HttpServletRequest
     */
    private static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("当前不在Web请求上下文中");
        }
        return attributes.getRequest();
    }

    /**
     * 设备信息封装类
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceInfo {
        // 浏览器信息
        private String browserName;          // 浏览器名称，如 "Chrome"
        private String browserType;          // 浏览器类型，如 "Web Browser"
        private String browserVersion;       // 浏览器版本，如 "91.0.4472.124"
        private String browserGroup;         // 浏览器组，如 "Chrome"
        private String browserManufacturer;  // 浏览器厂商，如 "Google Inc"

        // 操作系统信息
        private String osName;              // 操作系统名称，如 "Windows 10"
        private String osDeviceType;        // 设备类型，如 "Computer"
        private String osGroup;             // 操作系统组，如 "Windows"
        private String osManufacturer;      // 操作系统厂商，如 "Microsoft Corporation"

        // 其他信息
        private Boolean mobile;             // 是否为移动设备
        private String userAgent;           // 原始 User-Agent 字符串

        /**
         * 获取简化信息
         */
        public String getSimpleInfo() {
            return String.format("%s %s on %s",
                    browserName,
                    browserVersion != null ? browserVersion : "",
                    osName);
        }

        /**
         * 是否为 Windows 系统
         */
        public boolean isWindows() {
            return osGroup != null && osGroup.contains("Windows");
        }

        /**
         * 是否为 macOS 系统
         */
        public boolean isMacOS() {
            return osGroup != null && osGroup.contains("Mac OS");
        }

        /**
         * 是否为 Linux 系统
         */
        public boolean isLinux() {
            return osGroup != null && osGroup.contains("Linux");
        }

        /**
         * 是否为 iOS 系统
         */
        public boolean isIOS() {
            return osName != null && osName.contains("iOS");
        }

        /**
         * 是否为 Android 系统
         */
        public boolean isAndroid() {
            return osName != null && osName.contains("Android");
        }

        /**
         * 是否为 Chrome 浏览器
         */
        public boolean isChrome() {
            return browserGroup != null && browserGroup.contains("Chrome");
        }

        /**
         * 是否为 Firefox 浏览器
         */
        public boolean isFirefox() {
            return browserGroup != null && browserGroup.contains("Firefox");
        }

        /**
         * 是否为 Safari 浏览器
         */
        public boolean isSafari() {
            return browserGroup != null && browserGroup.contains("Safari");
        }

        /**
         * 是否为 Edge 浏览器
         */
        public boolean isEdge() {
            return browserGroup != null && browserGroup.contains("Edge");
        }

        /**
         * 转换为 Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("browserName", browserName);
            map.put("browserType", browserType);
            map.put("browserVersion", browserVersion);
            map.put("browserGroup", browserGroup);
            map.put("browserManufacturer", browserManufacturer);
            map.put("osName", osName);
            map.put("osDeviceType", osDeviceType);
            map.put("osGroup", osGroup);
            map.put("osManufacturer", osManufacturer);
            map.put("mobile", mobile);
            map.put("userAgent", userAgent);
            return map;
        }
    }
}
