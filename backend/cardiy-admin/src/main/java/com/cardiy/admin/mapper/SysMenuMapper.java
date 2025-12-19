package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysMenu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单权限 数据层
 */
@Repository
public interface SysMenuMapper extends MongoRepository<SysMenu, String> {
    
    /**
     * 根据业务ID查询菜单
     */
    SysMenu findByMenuId(String menuId);
    
    /**
     * 根据父菜单ID查询
     */
    List<SysMenu> findByParentIdOrderByOrderNumAsc(String parentId);
    
    /**
     * 根据状态查询菜单
     */
    List<SysMenu> findByStatusOrderByOrderNumAsc(String status);
}

