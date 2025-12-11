package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysUser;
import com.cardiy.admin.mapper.SysUserMapper;
import com.cardiy.admin.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户 业务层处理
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    
    @Autowired
    private SysUserMapper userMapper;
    
    @Override
    public SysUser save(SysUser user) {
        user.setUserId(IdUtil.getSnowflakeNextIdStr());
        return userMapper.save(user);
    }
    
    @Override
    public Optional<SysUser> findById(Long userId) {
        SysUser user = userMapper.findByUserId(userId);
        return Optional.ofNullable(user);
    }
    
    @Override
    public List<SysUser> findAll() {
        return userMapper.findAll();
    }
    
    @Override
    public Page<SysUser> findAll(Pageable pageable) {
        return userMapper.findAll(pageable);
    }
    
    @Override
    public List<SysUser> findByUserNameLike(String userName) {
        return userMapper.findByUserNameLike(userName);
    }
    
    @Override
    public List<SysUser> findByStatus(String status) {
        return userMapper.findByStatus(status);
    }
    
    @Override
    public void deleteById(Long userId) {
        SysUser user = userMapper.findByUserId(userId);
        if (user != null && user.getId() != null) {
            userMapper.deleteById(user.getId());
        }
    }
    
    @Override
    public void deleteAllById(List<Long> userIds) {
        for (Long userId : userIds) {
            deleteById(userId);
        }
    }
}

