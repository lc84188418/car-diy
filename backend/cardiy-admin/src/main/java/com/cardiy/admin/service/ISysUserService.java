package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 用户 业务层
 */
public interface ISysUserService {
    
    /**
     * 保存用户
     */
    SysUser save(SysUser user);
    
    /**
     * 根据ID查询用户
     */
    Optional<SysUser> findById(Long userId);
    
    /**
     * 查询所有用户
     */
    List<SysUser> findAll();
    
    /**
     * 分页查询用户
     */
    Page<SysUser> findAll(Pageable pageable);
    
    /**
     * 根据条件查询用户
     */
    List<SysUser> findByUserNameLike(String userName);
    
    /**
     * 根据状态查询用户
     */
    List<SysUser> findByStatus(String status);
    
    /**
     * 删除用户
     */
    void deleteById(Long userId);
    
    /**
     * 批量删除用户
     */
    void deleteAllById(List<Long> userIds);
}

