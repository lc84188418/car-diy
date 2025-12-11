package com.cardiy.admin.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * 系统访问记录 sys_logininfor
 */
@Data
@Document(collection = "sys_logininfor")
public class SysLogininfor {
    
    private static final long serialVersionUID = 1L;

    /** MongoDB 的 _id 字段 */
    @Id
    private String id;
    
    /** 访问ID（业务ID） */
    @Field("infoId")
    private String infoId;

    /** 用户账号 */
    private String userName;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地点 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 登录状态（0成功 1失败） */
    private String status;

    /** 提示消息 */
    private String msg;

    /** 访问时间 */
    private LocalDateTime loginTime;
}

