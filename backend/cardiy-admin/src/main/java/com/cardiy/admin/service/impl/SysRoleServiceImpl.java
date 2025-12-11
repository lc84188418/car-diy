package com.cardiy.admin.service.impl;

import com.cardiy.admin.domain.SysRole;
import com.cardiy.admin.mapper.SysRoleMapper;
import com.cardiy.admin.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 角色 业务层处理
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    
    @Autowired
    private SysRoleMapper roleMapper;
    
    @Override
    public SysRole save(SysRole role) {
        return roleMapper.save(role);
    }
    
    @Override
    public Optional<SysRole> findById(Long roleId) {
        SysRole role = roleMapper.findByRoleId(roleId);
        return Optional.ofNullable(role);
    }
    
    @Override
    public List<SysRole> findAll() {
        return roleMapper.findAll();
    }
    
    @Override
    public Page<SysRole> findAll(Pageable pageable) {
        return roleMapper.findAll(pageable);
    }
    
    @Override
    public List<SysRole> findByStatus(String status) {
        return roleMapper.findByStatus(status);
    }
    
    @Override
    public void deleteById(Long roleId) {
        SysRole role = roleMapper.findByRoleId(roleId);
        if (role != null && role.getId() != null) {
            roleMapper.deleteById(role.getId());
        }
    }
    
    @Override
    public void deleteAllById(List<Long> roleIds) {
        for (Long roleId : roleIds) {
            deleteById(roleId);
        }
    }
}

