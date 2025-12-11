package com.cardiy.car.service;

import com.cardiy.car.domain.CarModelOption;

import java.util.List;
import java.util.Optional;

/**
 * 车型配置选项服务接口
 */
public interface ICarModelOptionService {
    
    /**
     * 保存配置选项
     */
    CarModelOption save(CarModelOption option);
    
    /**
     * 根据ID查询配置选项
     */
    Optional<CarModelOption> findById(String id);
    
    /**
     * 查询所有配置选项
     */
    List<CarModelOption> findAll();
    
    /**
     * 根据车型ID查询配置选项
     */
    List<CarModelOption> findByModelId(String modelId);
    
    /**
     * 根据车型ID和配置类型查询
     */
    List<CarModelOption> findByModelIdAndOptionType(String modelId, String optionType);
    
    /**
     * 根据配置类型查询
     */
    List<CarModelOption> findByOptionType(String optionType);
    
    /**
     * 根据状态查询配置选项
     */
    List<CarModelOption> findByStatus(String status);
    
    /**
     * 删除配置选项
     */
    void deleteById(String id);
    
    /**
     * 批量删除配置选项
     */
    void deleteAllById(List<String> ids);
}

