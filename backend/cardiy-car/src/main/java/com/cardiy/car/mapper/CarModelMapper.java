package com.cardiy.car.mapper;

import com.cardiy.car.domain.CarModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelMapper extends MongoRepository<CarModel, String> {
    
    /**
     * 根据状态查询车型
     */
    List<CarModel> findByStatusOrderByCreateTimeDesc(String status);
}

