package com.cardiy.car.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 车型实体
 */
@Data
@Document(collection = "car_model")
public class CarModel {
    
    @Id
    private String id;
    
    private String brand;
    
    private String series;
    
    private String year;
    
    private String mainImageUrl;
    
    private String description;
    
    private String status;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
}

