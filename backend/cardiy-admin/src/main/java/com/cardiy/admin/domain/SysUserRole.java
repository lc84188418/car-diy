package com.cardiy.admin.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 用户角色对象 sys_user_role
 */
@Data
@Document(collection = "sys_user_role")
public class SysUserRole{
    
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Id
    private String id;  // MongoDB 的 _id 字段，使用 String 类型
    
    /** 用户ID（业务ID） */
    @Field("userId")
    private String userId;

    /** 角色id */
    private String roleId;
}

