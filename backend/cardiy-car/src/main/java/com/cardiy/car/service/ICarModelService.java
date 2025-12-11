package com.cardiy.car.service;

import com.cardiy.car.domain.CarModel;

import java.util.List;
import java.util.Optional;

public interface ICarModelService {
    
    /**
     * 保存车型
     */
    CarModel save(CarModel model);
    
    /**
     * 根据ID查询车型
     */
    Optional<CarModel> findById(String id);
    
    /**
     * 查询所有车型
     */
    List<CarModel> findAll();
    
    /**
     * 根据状态查询车型
     */
    List<CarModel> findByStatus(String status);
    
    /**
     * 删除车型
     */
    void deleteById(String id);
}

