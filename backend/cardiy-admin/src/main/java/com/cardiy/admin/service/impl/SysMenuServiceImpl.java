package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysMenu;
import com.cardiy.admin.mapper.SysMenuMapper;
import com.cardiy.admin.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 菜单权限 业务层处理
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public SysMenu save(SysMenu menu) {
        if(Objects.isNull(menu.getMenuId())){
            menu.setMenuId(IdUtil.getSnowflakeNextIdStr());
        }
        return menuMapper.save(menu);
    }

    @Override
    public Optional<SysMenu> findById(String menuId) {
        SysMenu menu = menuMapper.findByMenuId(menuId);
        return Optional.ofNullable(menu);
    }

    @Override
    public List<SysMenu> findAll() {
        return menuMapper.findAll();
    }

    @Override
    public List<SysMenu> findByParentId(String parentId) {
        return menuMapper.findByParentIdOrderByOrderNumAsc(parentId);
    }

    @Override
    public List<SysMenu> findByStatus(String status) {
        return menuMapper.findByStatusOrderByOrderNumAsc(status);
    }

    @Override
    public void deleteById(String menuId) {
        SysMenu menu = menuMapper.findByMenuId(menuId);
        if (menu != null && menu.getId() != null) {
            menuMapper.deleteById(menu.getId());
        }
    }

    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> tree = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId() == null || "-".equals(menu.getParentId())) {
                tree.add(menu);
            }
        }
        for (SysMenu menu : tree) {
            menu.setChildren(getChildren(menu, menus));
        }
        return tree;
    }

    private List<SysMenu> getChildren(SysMenu parent, List<SysMenu> all) {
        List<SysMenu> children = new ArrayList<>();
        for (SysMenu menu : all) {
            if (parent.getMenuId().equals(menu.getParentId())) {
                children.add(menu);
                menu.setChildren(getChildren(menu, all));
            }
        }
        return children.isEmpty() ? null : children;
    }
}

