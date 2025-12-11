package com.cardiy.car.mapper;

import com.cardiy.car.domain.CarModelOption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelOptionMapper extends MongoRepository<CarModelOption, String> {
    
    /**
     * 根据车型ID和状态查询配置选项
     */
    List<CarModelOption> findByModelIdAndStatusOrderBySortOrderAsc(String modelId, String status);
    
    /**
     * 根据车型ID、配置类型和状态查询
     */
    List<CarModelOption> findByModelIdAndOptionTypeAndStatusOrderBySortOrderAsc(
        String modelId, String optionType, String status);
}

