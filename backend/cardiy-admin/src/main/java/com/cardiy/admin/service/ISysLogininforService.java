package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysLogininfor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 系统访问日志 业务层
 */
public interface ISysLogininforService {
    
    SysLogininfor save(SysLogininfor logininfor);
    
    Optional<SysLogininfor> findById(Long infoId);
    
    Page<SysLogininfor> findAll(Pageable pageable);
    
    Page<SysLogininfor> findByLoginTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    
    void deleteById(Long infoId);
    
    void deleteAllById(List<Long> infoIds);
}

