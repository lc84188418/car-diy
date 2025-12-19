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

    // 品牌
    private String brand;
    // 车系
    private String series;
    // 年份
    private String year;
    // 主图URL
    private String mainImageUrl;
    // 车型描述
    private String description;
    // 状态
    private String status;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
}

