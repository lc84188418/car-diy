package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 岗位信息 数据层
 */
@Repository
public interface SysPostMapper extends MongoRepository<SysPost, String> {
    
    /**
     * 根据业务ID查询岗位
     */
    SysPost findByPostId(Long postId);
    
    /**
     * 根据岗位编码查询
     */
    SysPost findByPostCode(String postCode);
    
    /**
     * 根据状态查询岗位
     */
    List<SysPost> findByStatusOrderByPostSortAsc(String status);
}

