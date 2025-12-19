package com.cardiy.admin.controller.system;

import com.cardiy.admin.domain.SysRole;
import com.cardiy.admin.service.ISysRoleService;
import com.cardiy.admin.util.MongoQueryUtil;
import com.cardiy.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息
 */
@RestController
@RequestMapping("/api/admin/system/role")
public class SysRoleController {
    
    @Autowired
    private ISysRoleService roleService;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    public Result<Page<SysRole>> list(
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "roleKey", required = false) String roleKey,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));
        
        // 构建查询条件
        List<Criteria> criteriaList = new ArrayList<>();
        if (roleName != null && !roleName.isEmpty()) {
            Criteria roleNameCriteria = MongoQueryUtil.buildLikeCriteria("roleName", roleName);
            if (roleNameCriteria != null) {
                criteriaList.add(roleNameCriteria);
            }
        }
        if (roleKey != null && !roleKey.isEmpty()) {
            Criteria roleKeyCriteria = MongoQueryUtil.buildLikeCriteria("roleKey", roleKey);
            if (roleKeyCriteria != null) {
                criteriaList.add(roleKeyCriteria);
            }
        }
        if (status != null && !status.isEmpty()) {
            Criteria statusCriteria = MongoQueryUtil.buildEqualCriteria("status", status);
            if (statusCriteria != null) {
                criteriaList.add(statusCriteria);
            }
        }
        
        Criteria criteria = MongoQueryUtil.combineCriteria(criteriaList);
        Query query = criteriaList.isEmpty() ? new Query() : new Query(criteria);
        Page<SysRole> page = MongoQueryUtil.queryWithPage(mongoTemplate, query, SysRole.class, pageable);
        
        return Result.success(page);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @GetMapping(value = "/{roleId}")
    public Result<SysRole> getInfo(@PathVariable("roleId") String roleId) {
        return roleService.findById(roleId)
            .map(Result::success)
            .orElse(Result.error("角色不存在"));
    }

    /**
     * 新增角色
     */
    @PostMapping
    public Result<Void> add(@RequestBody SysRole role) {
        roleService.save(role);
        return Result.success();
    }

    /**
     * 修改角色
     */
    @PutMapping
    public Result<Void> edit(@RequestBody SysRole role) {
        roleService.save(role);
        return Result.success();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{roleIds}")
    public Result<Void> remove(@PathVariable String[] roleIds) {
        roleService.deleteAllById(List.of(roleIds));
        return Result.success();
    }
}

