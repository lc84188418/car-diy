package com.cardiy.admin.service.impl;

import com.cardiy.admin.domain.SysOperLog;
import com.cardiy.admin.mapper.SysOperLogMapper;
import com.cardiy.admin.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Page<SysOperLog> findByTimeBetween(Date startTime, Date endTime, Pageable pageable) {
        return operLogMapper.findByTimeBetween(startTime, endTime, pageable);
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

