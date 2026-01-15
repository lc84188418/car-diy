package com.cardiy.admin.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @ClassName:CommonUrlLogEntity
 * @Desc:通用url访问日志表
 * @Author: liuchun
 * @CreateTime:2024/9/23 1:02
 * @Version:V1.31.0
 **/
@Data
@Document(collection = "common_url_log")
public class CommonUrlLogEntity implements Serializable {
    /**
     * MongoDB 的 _id 字段
     */
    @Id
    private String id;

    /**
     * url
     */
    private String url;

    /**
     * 请求方法名，get/post
     */
    private String methodName;

    /**
     * 时间，日，如20240923
     */
    private Integer day;

    /**
     * 时间，月，如202409
     */
    private Integer month;

    /**
     * 时间，年，如2024
     */
    private Integer year;

    /**
     * 请求次数
     */
    private Long count;
    /**
     * 是否规范请求，1是0否
     */
    private Integer isStandard;
    /**
     * 是否内部调用，1是0否
     */
    private Integer isInner;
}
