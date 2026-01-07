package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysOperLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 操作日志 业务层
 */
public interface ISysOperLogService {
    
    SysOperLog save(SysOperLog operLog);
    
    Optional<SysOperLog> findById(String operId);
    
    Page<SysOperLog> findAll(Pageable pageable);
    
    Page<SysOperLog> findByTimeBetween(Date startTime, Date endTime, Pageable pageable);
    
    void deleteById(String operId);
    
    void deleteAllById(List<String> operIds);
}

