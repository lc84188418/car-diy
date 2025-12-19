package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysOperLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 操作日志 业务层
 */
public interface ISysOperLogService {
    
    SysOperLog save(SysOperLog operLog);
    
    Optional<SysOperLog> findById(String operId);
    
    Page<SysOperLog> findAll(Pageable pageable);
    
    Page<SysOperLog> findByOperTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    
    void deleteById(String operId);
    
    void deleteAllById(List<String> operIds);
}

