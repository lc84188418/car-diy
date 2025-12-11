package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息 数据层
 */
@Repository
public interface SysUserMapper extends MongoRepository<SysUser, String> {
    
    /**
     * 根据用户名查询用户
     */
    SysUser findByUserName(String userName);
    
    /**
     * 根据用户名模糊查询
     */
    List<SysUser> findByUserNameLike(String userName);
    
    /**
     * 根据状态查询用户
     */
    List<SysUser> findByStatus(String status);
    
    /**
     * 根据业务ID（userId）查询用户
     */
    SysUser findByUserId(Long userId);
}

