package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 岗位信息 业务层
 */
public interface ISysPostService {
    
    SysPost save(SysPost post);
    
    Optional<SysPost> findById(Long postId);
    
    List<SysPost> findAll();
    
    Page<SysPost> findAll(Pageable pageable);
    
    List<SysPost> findByStatus(String status);
    
    void deleteById(Long postId);
    
    void deleteAllById(List<Long> postIds);
}

