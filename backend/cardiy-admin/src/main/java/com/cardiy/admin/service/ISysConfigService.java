package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 参数配置 业务层
 */
public interface ISysConfigService {
    
    SysConfig save(SysConfig config);
    
    Optional<SysConfig> findById(Long configId);
    
    Optional<SysConfig> findByConfigKey(String configKey);
    
    List<SysConfig> findAll();
    
    Page<SysConfig> findAll(Pageable pageable);
    
    void deleteById(Long configId);
    
    void deleteAllById(List<Long> configIds);
}

