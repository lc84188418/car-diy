package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysMenu;

import java.util.List;
import java.util.Optional;

/**
 * 菜单权限 业务层
 */
public interface ISysMenuService {
    
    SysMenu save(SysMenu menu);
    
    Optional<SysMenu> findById(Long menuId);
    
    List<SysMenu> findAll();
    
    List<SysMenu> findByParentId(Long parentId);
    
    List<SysMenu> findByStatus(String status);
    
    void deleteById(Long menuId);
    
    /**
     * 构建菜单树
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);
}

