package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysDept;

import java.util.List;
import java.util.Optional;

/**
 * 部门管理 业务层
 */
public interface ISysDeptService {
    
    SysDept save(SysDept dept);
    
    Optional<SysDept> findById(String deptId);
    
    List<SysDept> findAll();
    
    List<SysDept> findByParentId(String parentId);
    
    List<SysDept> findByStatus(String status);
    
    void deleteById(String deptId);
    
    /**
     * 构建部门树
     */
    List<SysDept> buildDeptTree(List<SysDept> depts);
}

