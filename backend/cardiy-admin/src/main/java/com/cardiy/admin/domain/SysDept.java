package com.cardiy.admin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 部门表 sys_dept
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "sys_dept")
public class SysDept extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** MongoDB 的 _id 字段 */
    @Id
    private String id;
    
    /** 部门ID（业务ID） */
    @Field("deptId")
    private String deptId;

    /** 父部门ID */
    @Field("parentId")
    private String parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 部门名称 */
    private String deptName;

    /** 显示顺序 */
    private Integer orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 部门状态（0正常 1停用） */
    private String status;

    /** 子部门 */
    @Transient
    private List<SysDept> children;
}

