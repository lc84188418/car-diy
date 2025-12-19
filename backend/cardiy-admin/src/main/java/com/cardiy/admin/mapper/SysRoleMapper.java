package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色信息 数据层
 */
@Repository
public interface SysRoleMapper extends MongoRepository<SysRole, String> {
    
    /**
     * 根据业务ID查询角色
     */
    SysRole findByRoleId(String roleId);
    
    /**
     * 根据角色名称查询
     */
    SysRole findByRoleName(String roleName);
    
    /**
     * 根据角色键名查询
     */
    SysRole findByRoleKey(String roleKey);
    
    /**
     * 根据状态查询角色
     */
    List<SysRole> findByStatus(String status);
}

