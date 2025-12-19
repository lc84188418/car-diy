package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysOperLog;
import com.cardiy.admin.mapper.SysOperLogMapper;
import com.cardiy.admin.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 操作日志 业务层处理
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {
    
    @Autowired
    private SysOperLogMapper operLogMapper;
    
    @Override
    public SysOperLog save(SysOperLog operLog) {
        operLog.setOperId(IdUtil.getSnowflakeNextIdStr());
        return operLogMapper.save(operLog);
    }
    
    @Override
    public Optional<SysOperLog> findById(String operId) {
        SysOperLog operLog = operLogMapper.findByOperId(operId);
        return Optional.ofNullable(operLog);
    }
    
    @Override
    public Page<SysOperLog> findAll(Pageable pageable) {
        return operLogMapper.findAll(pageable);
    }
    
    @Override
    public Page<SysOperLog> findByOperTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return operLogMapper.findByOperTimeBetween(startTime, endTime, pageable);
    }
    
    @Override
    public void deleteById(String operId) {
        SysOperLog operLog = operLogMapper.findByOperId(operId);
        if (operLog != null && operLog.getId() != null) {
            operLogMapper.deleteById(operLog.getId());
        }
    }
    
    @Override
    public void deleteAllById(List<String> operIds) {
        for (String operId : operIds) {
            deleteById(operId);
        }
    }
}

