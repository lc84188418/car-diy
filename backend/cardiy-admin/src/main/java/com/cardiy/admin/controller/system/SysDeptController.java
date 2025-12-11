package com.cardiy.admin.controller.system;

import com.cardiy.admin.domain.SysDept;
import com.cardiy.admin.service.ISysDeptService;
import com.cardiy.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息
 */
@RestController
@RequestMapping("/api/admin/system/dept")
public class SysDeptController {
    
    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表（树形结构）
     */
    @GetMapping("/list")
    public Result<List<SysDept>> list(@RequestParam(value = "deptName", required = false) String deptName,
                                      @RequestParam(value = "status", required = false) String status) {
        List<SysDept> depts;
        if (status != null && !status.isEmpty()) {
            depts = deptService.findByStatus(status);
        } else {
            depts = deptService.findAll();
        }
        
        // 构建部门树
        List<SysDept> tree = deptService.buildDeptTree(depts);
        return Result.success(tree);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping(value = "/{deptId}")
    public Result<SysDept> getInfo(@PathVariable("deptId") Long deptId) {
        return deptService.findById(deptId)
            .map(Result::success)
            .orElse(Result.error("部门不存在"));
    }

    /**
     * 新增部门
     */
    @PostMapping
    public Result<Void> add(@RequestBody SysDept dept) {
        deptService.save(dept);
        return Result.success();
    }

    /**
     * 修改部门
     */
    @PutMapping
    public Result<Void> edit(@RequestBody SysDept dept) {
        deptService.save(dept);
        return Result.success();
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    public Result<Void> remove(@PathVariable("deptId") Long deptId) {
        deptService.deleteById(deptId);
        return Result.success();
    }
}

