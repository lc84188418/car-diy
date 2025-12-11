package com.cardiy.admin.controller.system;

import com.cardiy.admin.domain.SysOperLog;
import com.cardiy.admin.service.ISysOperLogService;
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
 * 操作日志
 */
@RestController
@RequestMapping("/api/admin/system/operlog")
public class SysOperLogController {
    
    @Autowired
    private ISysOperLogService operLogService;

    /**
     * 获取操作日志列表
     */
    @GetMapping("/list")
    public Result<Page<SysOperLog>> list(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "operName", required = false) String operName,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime,
            @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "operTime"));
        Page<SysOperLog> page;
        
        if (beginTime != null && endTime != null) {
            page = operLogService.findByOperTimeBetween(beginTime, endTime, pageable);
        } else {
            page = operLogService.findAll(pageable);
        }
        
        return Result.success(page);
    }

    /**
     * 根据操作日志编号获取详细信息
     */
    @GetMapping(value = "/{operId}")
    public Result<SysOperLog> getInfo(@PathVariable("operId") Long operId) {
        return operLogService.findById(operId)
            .map(Result::success)
            .orElse(Result.error("操作日志不存在"));
    }

    /**
     * 删除操作日志
     */
    @DeleteMapping("/{operIds}")
    public Result<Void> remove(@PathVariable Long[] operIds) {
        operLogService.deleteAllById(List.of(operIds));
        return Result.success();
    }
}

