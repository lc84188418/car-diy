package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysUser;
import com.cardiy.admin.domain.SysUserPost;
import com.cardiy.admin.domain.SysUserRole;
import com.cardiy.admin.domain.vo.SysUserVo;
import com.cardiy.admin.mapper.SysUserMapper;
import com.cardiy.admin.mapper.SysUserPostMapper;
import com.cardiy.admin.mapper.SysUserRoleMapper;
import com.cardiy.admin.service.ISysUserService;
import com.cardiy.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 */
@Slf4j
@Service
public class SysUserServiceImpl implements ISysUserService {
    
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysUserPostMapper userPostMapper;

    @Override
    public SysUser save(SysUser user) {
        if(Objects.isNull(user.getUserId())){
            user.setUserId(IdUtil.getSnowflakeNextIdStr());
        }
        return userMapper.save(user);
    }
    
    @Override
    public Result<SysUserVo> findById(String userId) {
        SysUser user = userMapper.findByUserId(userId);
        Optional<SysUser> optional = Optional.ofNullable(user);
        if (optional.isEmpty()) {
            return Result.error("用户不存在");
        }
        //查询岗位
        List<SysUserPost> userPosts = userPostMapper.findByUserId(userId);
        //查询角色
        List<SysUserRole> userRoles = userRoleMapper.findByUserId(userId);
        SysUserVo  sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(user,sysUserVo);
        sysUserVo.setPostIds(userPosts.stream().map(SysUserPost::getPostId).collect(Collectors.toList()));
        sysUserVo.setRoleIds(userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        return Result.success(sysUserVo);
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
    public void deleteById(String userId) {
        SysUser user = userMapper.findByUserId(userId);
        if (user != null && user.getId() != null) {
            userMapper.deleteById(user.getId());
        }
    }
    
    @Override
    public void deleteAllById(List<String> userIds) {
        for (String userId : userIds) {
            deleteById(userId);
        }
    }
}

