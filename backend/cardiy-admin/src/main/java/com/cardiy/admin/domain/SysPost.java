package com.cardiy.admin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 岗位表 sys_post
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "sys_post")
public class SysPost extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** MongoDB 的 _id 字段 */
    @Id
    private String id;
    
    /** 岗位ID（业务ID） */
    @Field("postId")
    private String postId;

    /** 岗位编码 */
    private String postCode;

    /** 岗位名称 */
    private String postName;

    /** 岗位排序 */
    private Integer postSort;

    /** 状态（0正常 1停用） */
    private String status;
}

