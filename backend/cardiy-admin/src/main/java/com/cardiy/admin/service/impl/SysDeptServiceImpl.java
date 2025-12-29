package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysDept;
import com.cardiy.admin.mapper.SysDeptMapper;
import com.cardiy.admin.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 部门管理 业务层处理
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Override
    public SysDept save(SysDept dept) {
        if(Objects.isNull(dept.getDeptId())){
            dept.setDeptId(IdUtil.getSnowflakeNextIdStr());
        }
        return deptMapper.save(dept);
    }

    @Override
    public Optional<SysDept> findById(String deptId) {
        SysDept dept = deptMapper.findByDeptId(deptId);
        return Optional.ofNullable(dept);
    }

    @Override
    public List<SysDept> findAll() {
        return deptMapper.findAll(Sort.by(Sort.Direction.DESC,"orderNum"));
    }

    @Override
    public List<SysDept> findByParentId(String parentId) {
        return deptMapper.findByParentIdOrderByOrderNumAsc(parentId);
    }

    @Override
    public List<SysDept> findByStatus(String status) {
        return deptMapper.findByStatusOrderByOrderNumAsc(status);
    }

    @Override
    public void deleteById(String deptId) {
        SysDept dept = deptMapper.findByDeptId(deptId);
        if (dept != null && dept.getId() != null) {
            deptMapper.deleteById(dept.getId());
        }
    }

    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> tree = new ArrayList<>();
        //先获取到根节点，也就是父ID为"-"的部门
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || "0".equals(dept.getParentId())) {
                tree.add(dept);
            }
        }
        for (SysDept dept : tree) {
            dept.setChildren(getChildren(dept, depts));
        }
        return tree;
    }

    private List<SysDept> getChildren(SysDept parent, List<SysDept> all) {
        List<SysDept> children = new ArrayList<>();
        for (SysDept dept : all) {
            if (parent.getDeptId().equals(dept.getParentId())) {
                children.add(dept);
                dept.setChildren(getChildren(dept, all));
            }
        }
        return children.isEmpty() ? null : children;
    }
}

