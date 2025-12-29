package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysUserPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户岗位信息 数据层
 */
@Repository
public interface SysUserPostMapper extends MongoRepository<SysUserPost, String> {

    /**
     * 根据用户id查询
     */
    List<SysUserPost> findByUserId(String userId);

    void deleteAllByUserId(String userId);
}

