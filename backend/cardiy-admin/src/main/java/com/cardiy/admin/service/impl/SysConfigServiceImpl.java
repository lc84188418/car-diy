package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysConfig;
import com.cardiy.admin.mapper.SysConfigMapper;
import com.cardiy.admin.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 参数配置 业务层处理
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {
    
    @Autowired
    private SysConfigMapper configMapper;
    
    @Override
    public SysConfig save(SysConfig config) {
        if(Objects.isNull(config.getConfigId())){
            config.setConfigId(IdUtil.getSnowflakeNextIdStr());
        }
        return configMapper.save(config);
    }
    
    @Override
    public Optional<SysConfig> findById(String configId) {
        SysConfig config = configMapper.findByConfigId(configId);
        return Optional.ofNullable(config);
    }
    
    @Override
    public Optional<SysConfig> findByConfigKey(String configKey) {
        SysConfig config = configMapper.findByConfigKey(configKey);
        return Optional.ofNullable(config);
    }
    
    @Override
    public List<SysConfig> findAll() {
        return configMapper.findAll();
    }
    
    @Override
    public Page<SysConfig> findAll(Pageable pageable) {
        return configMapper.findAll(pageable);
    }
    
    @Override
    public void deleteById(String configId) {
        SysConfig config = configMapper.findByConfigId(configId);
        if (config != null && config.getId() != null) {
            configMapper.deleteById(config.getId());
        }
    }
    
    @Override
    public void deleteAllById(List<String> configIds) {
        for (String configId : configIds) {
            deleteById(configId);
        }
    }
}

