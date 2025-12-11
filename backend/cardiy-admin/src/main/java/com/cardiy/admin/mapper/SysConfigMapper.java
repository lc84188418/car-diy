package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 参数配置 数据层
 */
@Repository
public interface SysConfigMapper extends MongoRepository<SysConfig, String> {
    
    /**
     * 根据业务ID查询参数配置
     */
    SysConfig findByConfigId(Long configId);
    
    /**
     * 根据参数键名查询
     */
    SysConfig findByConfigKey(String configKey);
}

