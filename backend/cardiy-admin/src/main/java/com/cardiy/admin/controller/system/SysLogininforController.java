package com.cardiy.admin.controller.system;

import com.cardiy.admin.domain.SysLogininfor;
import com.cardiy.admin.service.ISysLogininforService;
import com.cardiy.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统访问日志
 */
@RestController
@RequestMapping("/api/admin/system/logininfor")
public class SysLogininforController {
    
    @Autowired
    private ISysLogininforService logininforService;

    /**
     * 获取登录日志列表
     */
    @GetMapping("/list")
    public Result<Page<SysLogininfor>> list(
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "ipaddr", required = false) String ipaddr,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime,
            @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "loginTime"));
        Page<SysLogininfor> page;
        
        if (beginTime != null && endTime != null) {
            page = logininforService.findByLoginTimeBetween(beginTime, endTime, pageable);
        } else {
            page = logininforService.findAll(pageable);
        }
        
        return Result.success(page);
    }

    /**
     * 根据访问编号获取详细信息
     */
    @GetMapping(value = "/{infoId}")
    public Result<SysLogininfor> getInfo(@PathVariable("infoId") Long infoId) {
        return logininforService.findById(infoId)
            .map(Result::success)
            .orElse(Result.error("登录日志不存在"));
    }

    /**
     * 删除登录日志
     */
    @DeleteMapping("/{infoIds}")
    public Result<Void> remove(@PathVariable Long[] infoIds) {
        logininforService.deleteAllById(List.of(infoIds));
        return Result.success();
    }
}

