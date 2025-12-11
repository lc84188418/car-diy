package com.cardiy.admin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 参数配置表 sys_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "sys_config")
public class SysConfig extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** MongoDB 的 _id 字段 */
    @Id
    private String id;
    
    /** 参数主键（业务ID） */
    @Field("configId")
    private String configId;

    /** 参数名称 */
    private String configName;

    /** 参数键名 */
    private String configKey;

    /** 参数键值 */
    private String configValue;

    /** 系统内置（Y是 N否） */
    private String configType;
}

