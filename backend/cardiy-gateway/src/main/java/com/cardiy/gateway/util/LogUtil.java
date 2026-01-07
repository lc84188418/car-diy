package com.cardiy.gateway.util;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liuchun
 * @version v2.4.0
 * @className LogUtil
 * @desc TODO
 * @createTime 2025/12/30 15:40
 */
@Slf4j
@Component
public class LogUtil {
    @Resource(name = "restUrlThreadPool")
    private ThreadPoolExecutor restUrlThreadPool;
    /**
     * 从URI提取业务模块标题
     */
    public String extractTitleFromUri(String uri) {
        if (uri == null || uri.isEmpty()) {
            return "其他";
        }
        // 根据URI路径推断业务模块
        if (uri.contains("/system/user")) {
            return "用户管理";
        } else if (uri.contains("/system/role")) {
            return "角色管理";
        } else if (uri.contains("/system/menu")) {
            return "菜单管理";
        } else if (uri.contains("/system/dept")) {
            return "部门管理";
        } else if (uri.contains("/system/post")) {
            return "岗位管理";
        } else if (uri.contains("/system/dict")) {
            return "字典管理";
        } else if (uri.contains("/system/config")) {
            return "参数配置";
        } else if (uri.contains("/system/operlog")) {
            return "操作日志";
        } else if (uri.contains("/system/logininfor")) {
            return "登录日志";
        } else {
            return "其他";
        }
    }

    /**
     * 异步保存操作日志
     */
//    public void saveOperLog(SysOperLog operLog, String errorMsg, HttpStatusCode status) {
//        CompletableFuture.runAsync(() -> {
//            try {
//                // 设置响应结果
//                if (errorMsg != null) {
//                    // 限制响应体长度，避免过长
//                    if (errorMsg.length() > 2000) {
//                        operLog.setErrorMsg(errorMsg.substring(0, 2000) + "...(已截断)");
//                    } else {
//                        operLog.setErrorMsg(errorMsg);
//                    }
//                }
//                // 保存日志
//                operLogService.save(operLog);
//                log.debug("操作日志保存成功: {}", operLog.getOperUrl());
//            } catch (Exception e) {
//                log.error("保存操作日志失败: {}", e.getMessage(), e);
//            }
//        }, restUrlThreadPool);
//    }

}
