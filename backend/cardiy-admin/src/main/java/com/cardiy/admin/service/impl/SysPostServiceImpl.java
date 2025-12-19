package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysPost;
import com.cardiy.admin.mapper.SysPostMapper;
import com.cardiy.admin.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 岗位信息 业务层处理
 */
@Service
public class SysPostServiceImpl implements ISysPostService {
    
    @Autowired
    private SysPostMapper postMapper;
    
    @Override
    public SysPost save(SysPost post) {
        if(Objects.isNull(post.getPostId())){
            post.setPostId(IdUtil.getSnowflakeNextIdStr());
        }
        return postMapper.save(post);
    }
    
    @Override
    public Optional<SysPost> findById(String postId) {
        SysPost post = postMapper.findByPostId(postId);
        return Optional.ofNullable(post);
    }
    
    @Override
    public List<SysPost> findAll() {
        return postMapper.findAll();
    }
    
    @Override
    public Page<SysPost> findAll(Pageable pageable) {
        return postMapper.findAll(pageable);
    }
    
    @Override
    public List<SysPost> findByStatus(String status) {
        return postMapper.findByStatusOrderByPostSortAsc(status);
    }
    
    @Override
    public void deleteById(String postId) {
        SysPost post = postMapper.findByPostId(postId);
        if (post != null && post.getId() != null) {
            postMapper.deleteById(post.getId());
        }
    }
    
    @Override
    public void deleteAllById(List<String> postIds) {
        for (String postId : postIds) {
            deleteById(postId);
        }
    }
}

