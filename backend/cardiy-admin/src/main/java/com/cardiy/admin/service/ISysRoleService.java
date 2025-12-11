package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 角色 业务层
 */
public interface ISysRoleService {
    
    SysRole save(SysRole role);
    
    Optional<SysRole> findById(Long roleId);
    
    List<SysRole> findAll();
    
    Page<SysRole> findAll(Pageable pageable);
    
    List<SysRole> findByStatus(String status);
    
    void deleteById(Long roleId);
    
    void deleteAllById(List<Long> roleIds);
}

