package com.cardiy.admin.domain.vo;

import com.cardiy.admin.domain.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户对象 sys_user
 */
@Data
public class SysUserVo extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private String id;  // MongoDB 的 _id 字段，使用 String 类型
    
    /** 用户ID（业务ID） */
    private String userId;

    /** 部门ID */
    private String deptId;

    /** 用户账号 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 用户类型（00系统用户） */
    private String userType;

    /** 用户邮箱 */
    private String email;

    /** 手机号码 */
    private String tel;

    /** 用户性别（0男 1女 2未知） */
    private String sex;

    /** 头像地址 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    private LocalDateTime loginDate;

    /** 备注 */
    private String remark;
    /**
     * 岗位ID列表
     */
    private List<String> postIds;
    /**
     * 角色ID列表
     */
    private List<String> roleIds;
}

