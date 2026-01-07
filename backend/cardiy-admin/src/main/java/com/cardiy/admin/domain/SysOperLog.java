package com.cardiy.admin.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 操作日志记录 sys_oper_log
 */
@Data
@Document(collection = "sys_oper_log")
public class SysOperLog {

    private static final long serialVersionUID = 1L;

    /**
     * MongoDB 的 _id 字段
     */
    @Id
    private String id;

    /**
     * 日志主键（业务ID）/请求头中reqId
     * 不一定唯一
     */
    @Field("operId")
    private String operId;

    /**
     * 业务模块
     */
    private String title;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 请求URI
     */
    private String uri;

    /**
     * 主机地址
     */
    private String ip;
    /**
     * 操作地点
     */
    private String location;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 请求参数
     */
    private String param;
    /**
     * 请求类型
     */
    private String contentType;
    /**
     * 操作时间
     */
    private Date time;

//    /** 返回参数 */
//    private String operResult;

    /**
     * 错误消息
     */
    private String errorMsg;
    /**
     * 请求执行时间
     */
    private Long executeTime;

    /**
     * http状态码
     */
    private Integer httpStatusCode;
    /**
     * 接口响应状态码，200成功，其他失败
     */
    private Integer respCode;
}

