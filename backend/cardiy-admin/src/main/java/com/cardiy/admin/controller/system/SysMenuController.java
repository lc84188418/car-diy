package com.cardiy.admin.controller.system;

import com.cardiy.admin.domain.SysMenu;
import com.cardiy.admin.service.ISysMenuService;
import com.cardiy.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单权限
 */
@RestController
@RequestMapping("/api/admin/system/menu")
public class SysMenuController {
    
    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单列表（树形结构）
     */
    @GetMapping("/list")
    public Result<List<SysMenu>> list(@RequestParam(value = "menuName", required = false) String menuName,
                                      @RequestParam(value = "status", required = false) String status) {
        List<SysMenu> menus;
        if (status != null && !status.isEmpty()) {
            menus = menuService.findByStatus(status);
        } else {
            menus = menuService.findAll();
        }
        
        // 构建菜单树
        List<SysMenu> tree = menuService.buildMenuTree(menus);
        return Result.success(tree);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    public Result<SysMenu> getInfo(@PathVariable("menuId") Long menuId) {
        return menuService.findById(menuId)
            .map(Result::success)
            .orElse(Result.error("菜单不存在"));
    }

    /**
     * 新增菜单
     */
    @PostMapping
    public Result<Void> add(@RequestBody SysMenu menu) {
        menuService.save(menu);
        return Result.success();
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public Result<Void> edit(@RequestBody SysMenu menu) {
        menuService.save(menu);
        return Result.success();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public Result<Void> remove(@PathVariable("menuId") Long menuId) {
        menuService.deleteById(menuId);
        return Result.success();
    }
}

