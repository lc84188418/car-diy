package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysUserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色信息 数据层
 */
@Repository
public interface SysUserRoleMapper extends MongoRepository<SysUserRole, String> {

    /**
     * 根据用户id查询
     */
    List<SysUserRole> findByUserId(String userId);

    void deleteAllByUserId(String userId);
}

