package com.cardiy.admin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 角色对象 sys_role
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "sys_role")
public class SysRole extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** MongoDB 的 _id 字段 */
    @Id
    private String id;
    
    /** 角色ID（业务ID） */
    @Field("roleId")
    private String roleId;

    /** 角色名称 */
    private String roleName;

    /** 角色权限字符串 */
    private String roleKey;

    /** 显示顺序 */
    private Integer roleSort;

    /** 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限） */
    private String dataScope;

    /** 菜单树选择项是否关联显示 */
    private Boolean menuCheckStrictly;

    /** 部门树选择项是否关联显示 */
    private Boolean deptCheckStrictly;

    /** 角色状态（0正常 1停用） */
    private String status;

    /** 备注 */
    private String remark;
}

