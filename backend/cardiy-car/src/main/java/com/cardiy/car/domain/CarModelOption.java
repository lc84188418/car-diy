package com.cardiy.car.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 车型配置选项实体
 */
@Data
@Document(collection = "car_model_option")
public class CarModelOption {
    
    @Id
    private String id;
    
    private String modelId;
    
    private String optionType; // paintColor, rim, tire, caliper
    
    private String code;
    
    private String name;
    
    private String previewColor;
    
    private String imageUrl;
    
    private String description;
    
    private Integer sortOrder;
    
    private String status;
}

