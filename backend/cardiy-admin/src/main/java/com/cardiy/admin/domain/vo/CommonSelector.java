package com.cardiy.admin.domain.vo;

import lombok.Data;

/**
 * @ClassName:CommonSelector
 * @Desc:通用选择器
 * @Author: liuchun
 * @CreateTime:2025/6/17 11:11
 * @Version:V1.42.0
 **/
@Data
public class CommonSelector {
    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 这个看具体的业务的状态
     */
    private String status;
}
