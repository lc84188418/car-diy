package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysRole;
import com.cardiy.admin.domain.vo.CommonSelector;
import com.cardiy.admin.mapper.SysRoleMapper;
import com.cardiy.admin.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 角色 业务层处理
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public SysRole save(SysRole role) {
        if(Objects.isNull(role.getRoleId())){
            role.setRoleId(IdUtil.getSnowflakeNextIdStr());
        }
        return roleMapper.save(role);
    }
    
    @Override
    public Optional<SysRole> findById(String roleId) {
        SysRole role = roleMapper.findByRoleId(roleId);
        return Optional.ofNullable(role);
    }
    
    @Override
    public List<SysRole> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public List<CommonSelector> findAllSelector(String roleName, String roleKey) {
        Criteria criteria = new Criteria();
        criteria.and("status").is("0");
        Query query = new Query(criteria);
        //排序
        query.with(Sort.by(Sort.Direction.DESC, "roleSort"));
        List<SysRole> roles = mongoTemplate.find(query, SysRole.class);
        List<CommonSelector> selectors = new ArrayList<>(roles.size());
        //转为CommonSelector
        for (SysRole role : roles) {
            CommonSelector selector = new CommonSelector();
            selector.setId(role.getRoleId());
            selector.setName(role.getRoleName());
            selector.setStatus(role.getStatus());
            selectors.add(selector);
        }
        return selectors;
    }

    @Override
    public Page<SysRole> findAll(Pageable pageable) {
        return roleMapper.findAll(pageable);
    }
    
    @Override
    public List<SysRole> findByStatus(String status) {
        return roleMapper.findByStatus(status);
    }
    
    @Override
    public void deleteById(String roleId) {
        SysRole role = roleMapper.findByRoleId(roleId);
        if (role != null && role.getId() != null) {
            roleMapper.deleteById(role.getId());
        }
    }
    
    @Override
    public void deleteAllById(List<String> roleIds) {
        for (String roleId : roleIds) {
            deleteById(roleId);
        }
    }
}

