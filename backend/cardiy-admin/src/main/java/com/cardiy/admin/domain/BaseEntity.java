package com.cardiy.admin.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * 包含审计字段：创建者、创建时间、更新者、更新时间
 * 
 * 注意：需要启用 MongoDB 审计功能才能自动填充这些字段
 * 在主类中使用 @EnableMongoAuditing 注解启用
 */
@Data
public class BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 创建者 */
    @CreatedBy
    @Field("createBy")
    private String createBy;

    /** 创建时间 */
    @CreatedDate
    @Field("createTime")
    private LocalDateTime createTime;

    /** 更新者 */
    @LastModifiedBy
    @Field("updateBy")
    private String updateBy;

    /** 更新时间 */
    @LastModifiedDate
    @Field("updateTime")
    private LocalDateTime updateTime;

    /** 删除标志（0代表存在 2代表删除） */
    @Field("delFlag")
    private String delFlag;
}

