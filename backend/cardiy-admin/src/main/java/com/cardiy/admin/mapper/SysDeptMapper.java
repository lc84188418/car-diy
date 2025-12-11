package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysDept;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门管理 数据层
 */
@Repository
public interface SysDeptMapper extends MongoRepository<SysDept, String> {
    
    /**
     * 根据业务ID查询部门
     */
    SysDept findByDeptId(Long deptId);
    
    /**
     * 根据父部门ID查询
     */
    List<SysDept> findByParentIdOrderByOrderNumAsc(Long parentId);
    
    /**
     * 根据状态查询部门
     */
    List<SysDept> findByStatusOrderByOrderNumAsc(String status);
}

